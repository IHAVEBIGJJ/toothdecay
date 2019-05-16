package com.example.jason.toothdecay;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class toothproblem extends AppCompatActivity {
    private Button back;
    private TextView problem;
    private TextView problem2;
    private ImageView tartar;
    private ImageView tartar2;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toothproblem);
        problem = (TextView)findViewById(R.id.textView15);
        problem2 = (TextView)findViewById(R.id.textView16);
        tartar = (ImageView)findViewById(R.id.imageView5);
        tartar2 = (ImageView)findViewById(R.id.imageView6);
        back = (Button)findViewById(R.id.button18);
        problem.setMovementMethod(ScrollingMovementMethod.getInstance());
        problem2.setMovementMethod(ScrollingMovementMethod.getInstance());
        problem.setText("牙結石:\n" +
                "牙結石的檢測可以透過肉眼的方式進行自我檢測，通常牙結石會結附在牙齒的齒頸附近，造成牙齦的感染演變成發炎狀況。\n" + "使用肉眼檢測牙結石時，對著鏡子觀察,可見的牙結石一般覆蓋在牙縫、牙根等「隱秘」的地方,肉眼可以看得到一層黃色、棕色或黑色的牙垢,這一般就是牙結石，如下圗所示。\n" +
                "\n" + "其中解決方法為:\n" +
                        "1. 有效刷牙\n" +
                        "\n" +
                        "認真刷牙≠有效刷牙，這就是為什麼有的人天天刷牙還是各種牙病纏身的原因，牙結石很容易在牙齒的內側形成，而有多少人刷牙時只刷外側，而且可以配合牙線，漱口水一起清潔口腔。\n" +
                        "\n" + "\n" + "\n" +
                        "2. 飲食控制\n" +
                        "\n" +
                        "少吃甜食，少吃黏性大的食物，多吃富含維生素的粗纖維食物。\n" +
                        "\n" +
                        "\n" +
                        "3. 定期檢查\n" +
                        "\n" +
                        "\n" +
                        "養成每年去檢查牙齒的習慣，至少一年一次，及時洗牙清除牙結石。\n" +
                        "\n" +
                        "\n" +
                        "4、超聲波洗牙\n" +
                        "\n" +
                        "\n" +
                        "每半年一次的超聲波洗牙是清除牙結石最為有效的辦法，也是治療牙齦炎、牙齦出血、牙周病的常規方法。\n" +
                        "\n" +
                        "\n" +
                        "5、牙齒漂白法\n" +
                        "\n" +
                        "\n" +
                        "利用化學藥物的方法置換出牙齒表面或牙面的色素成份讓牙齒變白，美容措施，對待附著牢固的牙結石其實不可取,長期療效並不穩定。\n" +
                        "\n" +
                        "\n" +
                        "6、化學溶解法\n" +
                        "\n" +
                        "\n" +
                        "國際上有些牙科機構通過採取1%的二磷酸化合物溶液抑制牙垢中的礦物質沉積來控制牙石的形成也有一定的效果,但是目前沒有廣泛推廣，只適合少量較軟的牙垢，對於已經形成附著很久的結石,超聲波洗牙、噴沙潔牙仍然是不二之法。\n" +
                        "\n" +
                        "\n" +
                        "\n"
                );
        problem2.setText("口腔內出現血泡:\n" + "在肉眼觀察中可明顯察覺到其患部的異狀，可發生於口腔粘膜的任何部位，以唇、頰、舌部多見，嚴重者可以波及咽部粘膜。不少患者隨著病程的延長，潰瘍面積增大，數目增多，疼痛加重，癒合期延長，間隔期縮短等，影響食和說話。\n" +
                "\n" +
                "口腔內黏膜會長血泡主要是受細微外傷所致，而並非攝取不潔食物，例如口腔黏膜被魚刺刺傷、刷牙時不慎撞傷等，致使血水滲出時，在口腔壁形成血泡，由於口腔內細菌很多，建議勿自行刺破，以免引發後續感染。 \n" +
                "一般而言，人體免疫系統會讓血泡在3至7天內癒合，若血泡超過2周未癒合，很可能是糖尿病或其它慢性疾病影響免疫功能，或有魚刺等異物卡在口腔黏膜內未取出所致，應就醫。" +
                "其預防方式為：\n "+ "1、注意口腔衛生，避免損傷口腔黏膜，避免辛辣性食物和局部刺激。\n" +
                "\n" +
                "2、保持心情舒暢，樂觀開朗，避免事情和著急。\n" +
                "\n" +
                "3、保證充足的睡眠時間，避免過度疲勞。\n" +
                "4、注意生活規律性和營養均衡性，養成一定排便習慣，防止便秘。\n" +
                "\n" +
                "\n"

        );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
