package com.example.mp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private MediaView mediaview;
    @FXML
    private Button backwardbutton;
    @FXML
    private Button forwardbutton;

    @FXML
    private Button playbutton;
    @FXML
    private Slider timeslider;

    private MediaPlayer player;

    @FXML
    void opensongmenu(ActionEvent event) {
        try {
            // Use FileChooser to select a media file
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            if (file != null) {
                Media media = new Media(file.toURI().toString());
                if(player!=null)
                {
                    player.dispose();
                }
                player = new MediaPlayer(media);
                mediaview.setMediaPlayer(player);
                player.setOnReady(() ->
                {
                    timeslider.setMin(0);
                    timeslider.setMax(player.getMedia().getDuration().toMinutes());
                    timeslider.setValue(0);
                });


                player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                     Duration d=   player.getCurrentTime();
                        timeslider.setValue(d.toMinutes());


                    }
                });


                // time slider
                timeslider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        if(timeslider.isPressed())
                        {
                            double value =  timeslider.getValue();
                            player.seek(new Duration(value*60*1000));
                        }
                    }
                });


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void play(ActionEvent event) {

        if (player != null) {
            MediaPlayer.Status status = player.getStatus();
            if (status != MediaPlayer.Status.PLAYING) {
                player.play();
                try {
                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/icons/pause.png")));
                    imageView.setFitHeight(40);
                    imageView.setFitWidth(40);
                    playbutton.setGraphic(imageView);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            } else {
                player.pause();
                try {
                    ImageView imageView = new ImageView(new Image(new FileInputStream("src/icons/play2.png")));
                    imageView.setFitHeight(40);
                    imageView.setFitWidth(40);
                    playbutton.setGraphic(imageView);
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }


    }
    @FXML
    void postbtnclick(ActionEvent event) {
      double d = player.getCurrentTime().toSeconds();
        d= d+10;
    player.seek(new Duration(d*1000));


    }

    @FXML
    void prebtnclick(ActionEvent event) {
        double d = player.getCurrentTime().toSeconds();
        d= d-10;
        player.seek(new Duration(d*1000));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            ImageView imageViewplay = new ImageView(new Image(new FileInputStream("src/icons/play2.png")));
            ImageView imageViewback   = new ImageView(new Image(new FileInputStream("src/icons/backward.png")));
            ImageView imageViewforward =  new ImageView(new Image(new FileInputStream("src/icons/forward.png")));
            imageViewback.setFitWidth(40);
            imageViewback.setFitHeight(40);
            imageViewplay.setFitWidth(40);  // Set width
            imageViewplay.setFitHeight(40); // Set height
            imageViewforward.setFitHeight(40);
            imageViewforward.setFitWidth(40);
            backwardbutton.setGraphic(imageViewback);
            playbutton.setGraphic(imageViewplay);
            forwardbutton.setGraphic(imageViewforward);
            // Bind button size to parent container

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
