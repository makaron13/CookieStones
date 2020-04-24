package com.example.cookiestones;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvMovies;
    private Button [] buttonsC  = new Button[9];
    private Button btnRefresh;
    private int count = 0; //Считает количество ходов.
    private byte winCount = 0; //Если эта переменная будет == 9, то игрок победил.
    private int [] btnColor  = {0, 0, 0, 0, 0, 0, 0, 0, 0 }; //Этот массив будет определять цвет и надпись на кнопке.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Находим все элементы на экране Activity
        tvMovies = findViewById(R.id.tvMovies);

        buttonsC[0] = findViewById(R.id.btnTL0);
        buttonsC[1] = findViewById(R.id.btnTC1);
        buttonsC[2] = findViewById(R.id.btnTR2);
        buttonsC[3] = findViewById(R.id.btnCL3);
        buttonsC[4] = findViewById(R.id.btnCC4);
        buttonsC[5] = findViewById(R.id.btnCR5);
        buttonsC[6] = findViewById(R.id.btnBL6);
        buttonsC[7] = findViewById(R.id.btnBC7);
        buttonsC[8] = findViewById(R.id.btnBR8);

        btnRefresh = findViewById(R.id.btnRefresh);

        //Затаем служатель нажатий
        buttonsC[0].setOnClickListener(this);
        buttonsC[1].setOnClickListener(this);
        buttonsC[2].setOnClickListener(this);
        buttonsC[3].setOnClickListener(this);
        buttonsC[4].setOnClickListener(this);
        buttonsC[5].setOnClickListener(this);
        buttonsC[6].setOnClickListener(this);
        buttonsC[7].setOnClickListener(this);
        buttonsC[8].setOnClickListener(this);

        btnRefresh.setOnClickListener(this);

        //Первое создание игрового поля.
        randomColor();
        //Счетчик победы обнуляется, для правильного подсчета в дальнейшем.
        winCount = 0;
    }

    public void randomColor() {
        for (byte i = 0; i < 9; i++){
                buttonsC[i].setEnabled(true);
            //Выдает случайное число 0 или 1 для каждой.
            //переменной в массиве btnColor.
            btnColor[i] = (int) (Math.random()*2);
            //Если 0, то кнопка становится красной
            if (btnColor[i] == 0) {
                buttonsC[i].setBackgroundColor(Color.RED);
                buttonsC[i].setText("Stone");
            }
            //если 1, то кнопка становится зеленой.
            else{
                buttonsC[i].setBackgroundColor(Color.GREEN);
                buttonsC[i].setText("Cookie");
                winCount++; //Считает количество зеленых кнопок, чтобы случайно не получилась выиграшная комбинация.
                //Если зеленых кнопок 9 (что означает победу), то счетчик победы обнуляется и переменные
                //в массиве btnColor снова задаются случайным образом при помощи рекурсии.
                if(winCount == 9) {
                    winCount = 0;
                   randomColor();
                }
            }

        }


    }


    @Override
    public void onClick(View v) {
        //Каждое нажатие происходит инкрементация счетчика ходов.
        count++;

        //Далее происходит проверка, какая из кнопок нажата.
        //При нажатии на кнопку, происходит инкрементация переменной в массиве btnColor, привязанной к это кнопке
        //а так же инкрементация переменных соседних кнопок.
        //Соседними кнопками считаются кнопки, касающиеся друг друга с какой-либо стороны.
        switch (v.getId()){
            case R.id.btnTL0:
                btnColor[0]++;
                btnColor[1]++;
                btnColor[3]++;
                break;
            case R.id.btnTC1:
                btnColor[0]++;
                btnColor[1]++;
                btnColor[2]++;
                btnColor[4]++;
                break;
            case R.id.btnTR2:
                btnColor[1]++;
                btnColor[2]++;
                btnColor[5]++;
                break;
            case R.id.btnCL3:
                btnColor[0]++;
                btnColor[3]++;
                btnColor[4]++;
                btnColor[6]++;
                break;
            case R.id.btnCC4:
                btnColor[1]++;
                btnColor[3]++;
                btnColor[4]++;
                btnColor[5]++;
                btnColor[7]++;
                break;
            case R.id.btnCR5:
                btnColor[2]++;
                btnColor[4]++;
                btnColor[5]++;
                btnColor[8]++;
                break;
            case R.id.btnBL6:
                btnColor[3]++;
                btnColor[6]++;
                btnColor[7]++;
                break;
            case R.id.btnBC7:
                btnColor[4]++;
                btnColor[6]++;
                btnColor[7]++;
                btnColor[8]++;
                break;
            case R.id.btnBR8:
                btnColor[5]++;
                btnColor[7]++;
                btnColor[8]++;
                break;
                //Кнопка обновить случайным образом обновляет цвета кнопок. Вызывается метод randomColor.
            case R.id.btnRefresh:
                count = 0;
                randomColor();
                break;
        }
        //Здесь происходит проверка переменной кнопки и передача нужного цвета.
        //Если число делится на 2, то кнопка становится красной, если нет - то зеленой.
        for (byte i = 0; i < 9; i++) {
            if (btnColor[i]%2 == 0) {
                buttonsC[i].setBackgroundColor(Color.RED);
                buttonsC[i].setText("Stone");
            } else {
                buttonsC[i].setBackgroundColor(Color.GREEN);
                buttonsC[i].setText("Cookie");
                //Происходит инкрементация счетчика победы.
                //Подсчитывается каждая зеленая кнопка.
                winCount++;
            }
        }
        //Запись ходов.
        tvMovies.setText("Всего ходов: " + count);

        //Проверка условия победы.
        if(winCount == 9){
            for (byte i = 0; i < 9; i++){
                buttonsC[i].setEnabled(false);
                Toast.makeText(this,"Победа! Всего ходов: " + count, Toast.LENGTH_LONG).show();
            }
        }
        //Сброс счетчика победы.
        else {
            winCount = 0;
        }
    }
}
