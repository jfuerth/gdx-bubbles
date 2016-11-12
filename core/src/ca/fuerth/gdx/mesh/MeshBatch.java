package ca.fuerth.gdx.mesh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Arrays;

public class MeshBatch {

    private final ShaderProgram shader;
    private final Mesh mesh;
    private final OrthographicCamera cam;
    private float[] verts;

    private int idx = 0;


    public MeshBatch(int verticesPerBatch) {
        this.mesh = new Mesh(false, verticesPerBatch, 0,
                new VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
                VertexAttribute.ColorPacked());
        shader = createMeshShader(
                Gdx.files.internal("shaders/standard_vertex_shader.glsl").readString(),
                Gdx.files.internal("shaders/standard_fragment_shader.glsl").readString());
        verts = new float[verticesPerBatch * mesh.getVertexAttributes().vertexSize / 4];

        cam = new OrthographicCamera();
    }

    public void begin(Graphics graphics) {
        cam.setToOrtho(false, graphics.getWidth(), graphics.getHeight());
    }

    public void add(float x1, float y1,
                    float x2, float y2,
                    float x3, float y3,
                    Color color) {
        if (idx + (3 * mesh.getVertexAttributes().size()) >= verts.length) {
            System.out.println("Flushing before end() due to lack of space in verts");
            flush();
        }

        float colorFloatBits = color.toFloatBits();

        // TODO why are we copying these onto a java heap array? shouldn't we write these directly to the mesh?
        // need to measure if there's a performance difference, which means trying to run at max framerate
        verts[idx++] = x1;
        verts[idx++] = y1;
        verts[idx++] = colorFloatBits;

        verts[idx++] = x2;
        verts[idx++] = y2;
        verts[idx++] = colorFloatBits;

        verts[idx++] = x3;
        verts[idx++] = y3;
        verts[idx++] = colorFloatBits;
    }

    public void flush() {
        if (idx == 0) return;

        mesh.setVertices(verts);
        Gdx.gl.glDepthMask(false);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        int vertexCount = (idx / mesh.getVertexAttributes().size());

        shader.begin();

        shader.setUniformMatrix("u_projTrans", cam.combined);
        mesh.render(shader, GL20.GL_TRIANGLES, 0, vertexCount);
        shader.end();

        Gdx.gl.glDepthMask(true);

        Arrays.fill(verts, 0f);
        idx = 0;
    }

    public void end() {
        flush();
    }

    public void dispose() {
        shader.dispose();
        mesh.dispose();
    }

    private static ShaderProgram createMeshShader(String vertexShader, String fragmentShader) {
        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        String log = shader.getLog();
        if (!shader.isCompiled())
            throw new GdxRuntimeException(log);
        if (log != null && log.length() != 0)
            System.out.println("Shader Log: " + log);
        return shader;
    }

}
