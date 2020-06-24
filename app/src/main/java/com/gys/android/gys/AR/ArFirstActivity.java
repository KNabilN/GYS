package com.gys.android.gys.AR;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.gys.android.gys.R;

import java.util.Collection;

public class ArFirstActivity extends AppCompatActivity {
    private ExternalTexture externalTexture;
    private MediaPlayer mediaPlayer;
    private CustomArFragment arFragment;
    private Scene scene;
    private ModelRenderable renderable;
    private Boolean isImageDetected = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_first);

        externalTexture = new ExternalTexture();

        mediaPlayer = MediaPlayer.create(this, R.raw.video);
        mediaPlayer.setSurface(externalTexture.getSurface());
        mediaPlayer.setLooping(true);

        ModelRenderable
                .builder()
                .setSource(this, Uri.parse("video_screen.sfb"))
                .build()
                .thenAccept(modelRenderable -> {
                    modelRenderable.getMaterial().setExternalTexture("videotexture", externalTexture);
                    modelRenderable.getMaterial().setFloat4("KeyColor"
                            , new Color(0.01843f, 1f, 0.098f));
                    renderable = modelRenderable;

                });
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        scene = arFragment.getArSceneView().getScene();
        scene.addOnUpdateListener(this::onUpdate);


    }

    private void onUpdate(FrameTime frameTime) {

        if (isImageDetected)
            return;

        Frame frame = arFragment.getArSceneView().getArFrame();

        Collection <AugmentedImage> augmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);

        for (AugmentedImage image : augmentedImages){

            if (image.getTrackingState() == TrackingState.TRACKING){
                if (image.getName().equalsIgnoreCase("image")){

                    isImageDetected = true;

                    playVideos(image.createAnchor( image.getCenterPose()),image.getExtentX(),
                           image.getExtentZ() );
                    break;
                }
            }
        }

    }

    private void playVideos(Anchor anchor, float extentX, float extentZ) {
   mediaPlayer.start();
        AnchorNode anchorNode = new AnchorNode(anchor);

        externalTexture.getSurfaceTexture().setOnFrameAvailableListener(surfaceTexture -> {
            anchorNode.setRenderable(renderable);
            externalTexture.getSurfaceTexture().setOnFrameAvailableListener(null);
        });

        anchorNode.setWorldScale(new Vector3(extentX , 1f , extentZ));

        scene.addChild(anchorNode);
    }


}
