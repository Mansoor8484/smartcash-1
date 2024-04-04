package rebelalliance.smartcash.controller.scene;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import rebelalliance.smartcash.controller.BaseController;
import rebelalliance.smartcash.controller.IController;
import rebelalliance.smartcash.database.User;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

public class SecurityController extends BaseController implements IController {
    @FXML
    protected Button mfaEnableButton;

    @Override
    public void init() {
        this.addHeader();

        // Update UI if user has MFA enabled.
        if(this.sceneManager.getLoggedInUser().hasMfaEnabled()) {
            this.mfaEnableButton.setText("Disable MFA");
        }
    }

    @FXML
    protected void onMfaButtonClick() {
        User loggedInUser = this.sceneManager.getLoggedInUser();
        if(loggedInUser.hasMfaEnabled()) {
            // Disable MFA.
        }else {
            // Enable MFA.
            SecretGenerator secretGenerator = new DefaultSecretGenerator();
            String secret = secretGenerator.generate();
            QrData data = new QrData.Builder()
                    .label(loggedInUser.getEmail())
                    .secret(secret)
                    .issuer("SmartCash")
                    .algorithm(HashingAlgorithm.SHA1)
                    .digits(6)
                    .period(30)
                    .build();
            QrGenerator generator = new ZxingPngQrGenerator();
            String mimeType = generator.getImageMimeType();
            try {
                byte[] imageData = generator.generate(data);
                String dataUri = getDataUriForImage(imageData, mimeType);

                Stage popup;
                Scene scene;
                popup = new Stage();
                popup.setResizable(false);
                popup.setTitle("2FA Setup");

                VBox root = new VBox();
                root.setSpacing(5);
                root.setPadding(new Insets(5));
                root.setAlignment(Pos.TOP_CENTER);
                Text title = new Text("Scan this QR code with your authenticator app. If you can't scan it, you can manually enter the code below.");
                title.setWrappingWidth(400 - 10);
                ImageView qrCode = new ImageView(new Image(dataUri));
                qrCode.maxWidth(300);
                VBox codeBox = new VBox();
                Text secretText = new Text(secret);
                Button copyButton = new Button("Copy");
                copyButton.setOnAction(e -> {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();
                    content.putString(secret);
                    clipboard.setContent(content);
                });
                codeBox.setAlignment(Pos.CENTER);
                codeBox.getChildren().addAll(secretText, copyButton);
                Text afterScan = new Text("After scanning the QR code, enter the code generated by the authenticator app below.");
                afterScan.setWrappingWidth(400 - 10);
                TextField codeField = new TextField();
                Button submitButton = new Button("Submit");
                submitButton.setOnAction(e -> {
                    TimeProvider timeProvider = new SystemTimeProvider();
                    CodeGenerator codeGenerator = new DefaultCodeGenerator();
                    CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

                    boolean successful = verifier.isValidCode(secret, codeField.getText());

                    if(!successful) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid code.");
                        alert.setContentText("The 2FA code you entered is invalid. Please try again.");
                        alert.showAndWait();
                        return;
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("2FA enabled.");
                    alert.setContentText("2FA has been successfully enabled on your account.");
                    alert.showAndWait();

                    // TODO: Store secret in database.
                    // TODO: Update UI to reflect that MFA is enabled.
                });
                root.getChildren().addAll(
                        title,
                        qrCode,
                        codeBox,
                        afterScan,
                        codeField,
                        submitButton
                );

                scene = new Scene(root, 400, 600);
                popup.setScene(scene);
                popup.show();

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
