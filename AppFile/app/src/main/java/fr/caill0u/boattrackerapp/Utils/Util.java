package fr.caill0u.boattrackerapp.Utils;

import android.os.Build;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

import fr.caill0u.boattrackerapp.MainActivity;
import fr.caill0u.boattrackerapp.R;
import fr.caill0u.boattrackerapp.objects.Container;
import fr.caill0u.boattrackerapp.objects.ContainerShipType;
import fr.caill0u.boattrackerapp.objects.Containership;
import fr.caill0u.boattrackerapp.objects.Port;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class Util {
    public static Float getRandomFloat(float min, float max) {
        Random rand = new Random();
        float result = rand.nextFloat() * (max - min) + min;
        return result;
    }
    public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        int result = rand.nextInt() * (max - min) + min;
        return result;
    }
    public static LinearLayout createLayoutForMyContainer(Containership containership, MainActivity mainActivity) {
        LinearLayout ficheBateau = new LinearLayout(mainActivity);
        ficheBateau.setOrientation(LinearLayout.HORIZONTAL);
        ficheBateau.setBackgroundResource(R.drawable.border_bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ficheBateau.setElevation(10);
        }
        ficheBateau.setPadding(10,10,10,20);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        ficheBateau.setLayoutParams(paramsForFicheBateau);
        ImageView img = new ImageView(mainActivity);
        img.setBackgroundResource(R.drawable.ic_boaticon120);
        LinearLayout.LayoutParams paramsForImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForImage.gravity = Gravity.CENTER;
        paramsForImage.leftMargin = 15;
        img.setLayoutParams(paramsForImage);
        ficheBateau.addView(img);
        LinearLayout layoutTextBateau = new LinearLayout(mainActivity);
        layoutTextBateau.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramForLayoutOfText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForLayoutOfText.gravity = Gravity.CENTER;
        layoutTextBateau.setLayoutParams(paramForLayoutOfText);
        layoutTextBateau.addView(createText("Nom : " + containership.getName(), 13, mainActivity));
        layoutTextBateau.addView(createText("Id : " + containership.getId(), 15, mainActivity));
        ficheBateau.addView(layoutTextBateau);
        return ficheBateau;
    }
    public static LinearLayout createLayoutForMyPort(Port port, MainActivity mainActivity) {
        LinearLayout ficheBateau = new LinearLayout(mainActivity);
        ficheBateau.setOrientation(LinearLayout.HORIZONTAL);
        ficheBateau.setBackgroundResource(R.drawable.border_bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ficheBateau.setElevation(10);
        }
        ficheBateau.setPadding(10,10,10,20);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        ficheBateau.setLayoutParams(paramsForFicheBateau);
        ImageView img = new ImageView(mainActivity);
        img.setBackgroundResource(R.drawable.ic_encreicon);
        LinearLayout.LayoutParams paramsForImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForImage.gravity = Gravity.CENTER;
        paramsForImage.leftMargin = 15;
        img.setLayoutParams(paramsForImage);
        ficheBateau.addView(img);
        LinearLayout layoutTextBateau = new LinearLayout(mainActivity);
        layoutTextBateau.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramForLayoutOfText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForLayoutOfText.gravity = Gravity.CENTER;
        layoutTextBateau.setLayoutParams(paramForLayoutOfText);
        layoutTextBateau.addView(createText("Nom : " + port.getName(), 13, mainActivity));
        layoutTextBateau.addView(createText("Id : " + port.getId(), 15, mainActivity));
        ficheBateau.addView(layoutTextBateau);
        return ficheBateau;
    }
    private static TextView createText(String s, int size, MainActivity mainActivity) {
        TextView textView = new TextView(mainActivity);
        LinearLayout.LayoutParams paramForText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForText.gravity = Gravity.CENTER_HORIZONTAL;
        textView.setLayoutParams(paramForText);
        textView.setText(s);
        textView.setTextSize(size);
        return textView;
    }
    public static LinearLayout createLayoutForEditThisParameter(String s, MainActivity mainActivity, EditText editText){
        LinearLayout lnl = new LinearLayout(mainActivity);
        lnl.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        lnl.setLayoutParams(paramsForFicheBateau);
        lnl.addView(createText(s, 10, mainActivity));
        lnl.addView(editText);
        return lnl;
    }

    public static LinearLayout createLayoutForEditThisParameterForNumber(String s, MainActivity mainActivity, EditText editText){
        LinearLayout lnl = new LinearLayout(mainActivity);
        lnl.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        lnl.setLayoutParams(paramsForFicheBateau);
        lnl.addView(createText(s, 10, mainActivity));
        lnl.addView(editText);
        return lnl;
    }

    public static void createBoat() {
        for (int i=0; i<=15; i++) new Containership("Test", "Test", getRandomFloat(-90,90), getRandomFloat(-150,150), new ContainerShipType(getRandomInt(10,100),getRandomInt(10,100),getRandomInt(10,100),"Test"));
    }
    public static void createPort() {
        for (int i=0; i<=15; i++) new Port("Test", getRandomFloat(-90,90), getRandomFloat(-150,150));
    }
}
