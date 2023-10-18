package start;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbisInfo;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * ***Seulement format .wav est accepeté.*** Ou format .ogg (pas encore développé)
 * Donc si vous avez un format .mp3 ou l'autre format
 * Merci de les transformer à .wav sur outil internet.
 */
public class SoundLoaderGL {

    private int buffer;
    private int source;

    public SoundLoaderGL(String filepath) {
        buffer = AL10.alGenBuffers();

        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = loadWAV(filepath);
            AL10.alBufferData(buffer, AL10.AL_FORMAT_MONO16, pcm, 44100);
        }

        source = AL10.alGenSources();
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
    }

    public ShortBuffer loadWAV(String resource) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(resource));
            AudioFormat format = stream.getFormat();
            byte[] bytes = new byte[(int) (stream.getFrameLength() * format.getFrameSize())];
            int bytesRead = stream.read(bytes);

            ShortBuffer buffer = BufferUtils.createShortBuffer(bytesRead / 2);
            ByteBuffer temp = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
            while (temp.hasRemaining()) {
                buffer.put(temp.getShort());
            }
            buffer.flip();

            stream.close();
            return buffer;
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException("Failed to load WAV file", e);
        }
    }

    public void play() {
        // to control vitesse if need
        //AL10.alSourcef(source, AL10.AL_PITCH, 1.0f);
        AL10.alSourcePlay(source);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(source, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void stop() {
        AL10.alSourceStop(source);
    }

    public void cleanup() {
        AL10.alDeleteSources(source);
        AL10.alDeleteBuffers(buffer);
    }


}

