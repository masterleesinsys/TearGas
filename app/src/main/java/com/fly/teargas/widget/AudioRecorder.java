package com.fly.teargas.widget;

import android.media.MediaRecorder;
import android.os.Environment;

import com.fly.teargas.util.ImageUtil;

import java.io.File;
import java.io.IOException;


public class AudioRecorder {
    private static int SAMPLE_RATE_IN_HZ = 8000;  // 采样率

    private MediaRecorder mMediaRecorder;
    private String mPath;

    public AudioRecorder(String path) {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        this.mPath = sanitizePath(path);
    }

    private String sanitizePath(String path) {
        return ImageUtil.getTempImagePath() + path + ".acc";
    }

    public void start() throws IOException {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("SD Card is not mounted,It is  " + state
                    + ".");
        }
        File directory = new File(mPath).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created");
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        // recorder.setAudioChannels(AudioFormat.CHANNEL_CONFIGURATION_MONO);
        mMediaRecorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
        mMediaRecorder.setOutputFile(mPath);
        mMediaRecorder.prepare();
        mMediaRecorder.start();
    }

    public void stop() throws IOException {
        mMediaRecorder.stop();
        mMediaRecorder.release();
    }

    public double getAmplitude() {
        if (mMediaRecorder != null) {
            return (mMediaRecorder.getMaxAmplitude());
        } else
            return 0;
    }
}
