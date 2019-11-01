package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;


import java.io.IOException;
import java.util.List;


public class image extends AppCompatActivity {
    ImageView img;
    TextView res;
    Button ckimg;
    Button selimg;
    final int imgreq =1;
    Bitmap bitmap;
    String resultText;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        img=(ImageView) findViewById(R.id.img);
        res =(TextView)findViewById(R.id.result);
        ckimg=(Button)findViewById(R.id.ckimg);
        selimg=(Button)findViewById(R.id.selimg);
        ckimg.setEnabled(false);
        selimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,imgreq);
                res.setText("");
                ckimg.setEnabled(true);

            }

        });
        ckimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTextRecognition();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==imgreq && resultCode==RESULT_OK && data!=null){
            uri= data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img.setImageBitmap(bitmap);
                img.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runTextRecognition(){
      FirebaseVisionImage image =FirebaseVisionImage.fromBitmap(bitmap);
      FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
      ckimg.setEnabled(false);
      detector.processImage(image)
              .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                  @Override
                  public void onSuccess(FirebaseVisionText texts) {
                      ckimg.setEnabled(true);
                      processTextRecognitionResult(texts);
                  }
              })
      .addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              ckimg.setEnabled(true);
              e.printStackTrace();
          }
      });


    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        List<FirebaseVisionText.TextBlock> blocks =texts.getTextBlocks();
        if(blocks.size() ==0){
            ShowToast("No text found");
            return;
        }
        for(int i=0;i< blocks.size();i++){
            List<FirebaseVisionText.Line> lines= blocks.get(i).getLines();
            for (int j=0;j<lines.size();j++){
                res.setText(res.getText().toString()+"\n"+lines.get(j).getText());
                  }
        }
    }

    private void ShowToast(String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(image.this);
        builder.setMessage(message);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }
}
