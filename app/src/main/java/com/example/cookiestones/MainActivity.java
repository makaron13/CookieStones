package com.example.cookiestones;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//
public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private TextView tvMovies;
    private ImageView [] buttonsC  = new ImageView[9];
    private ImageView btnRefresh;
    private int count = 0; //Считает количество ходов.
    private byte winCount = 0; //Если эта переменная будет == 9, то игрок победил.
    private int [] btnColor  = {0, 0, 0, 0, 0, 0, 0, 0, 0 }; //Этот массив будет определять цвет и надпись на кнопке.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Находим все элементы на экране Activity
        tvMovies = findViewById(R.id.tvMovies);


        buttonsC[0] = findViewById(R.id.imTL0);
        buttonsC[1] = findViewById(R.id.imTC1);
        buttonsC[2] = findViewById(R.id.imTR2);
        buttonsC[3] = findViewById(R.id.imCL3);
        buttonsC[4] = findViewById(R.id.imCC4);
        buttonsC[5] = findViewById(R.id.imCR5);
        buttonsC[6] = findViewById(R.id.imBL6);
        buttonsC[7] = findViewById(R.id.imBC7);
        buttonsC[8] = findViewById(R.id.imBR8);

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
                buttonsC[i].setImageResource(R.drawable.stone);
            }
            //если 1, то кнопка становится зеленой.
            else{
                buttonsC[i].setImageResource(R.drawable.cookie);
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
            case R.id.imTL0:
                btnColor[0]++;
                btnColor[1]++;
                btnColor[3]++;
                break;
            case R.id.imTC1:
                btnColor[0]++;
                btnColor[1]++;
                btnColor[2]++;
                btnColor[4]++;
                break;
            case R.id.imTR2:
                btnColor[1]++;
                btnColor[2]++;
                btnColor[5]++;
                break;
            case R.id.imCL3:
                btnColor[0]++;
                btnColor[3]++;
                btnColor[4]++;
                btnColor[6]++;
                break;
            case R.id.imCC4:
                btnColor[1]++;
                btnColor[3]++;
                btnColor[4]++;
                btnColor[5]++;
                btnColor[7]++;
                break;
            case R.id.imCR5:
                btnColor[2]++;
                btnColor[4]++;
                btnColor[5]++;
                btnColor[8]++;
                break;
            case R.id.imBL6:
                btnColor[3]++;
                btnColor[6]++;
                btnColor[7]++;
                break;
            case R.id.imBC7:
                btnColor[4]++;
                btnColor[6]++;
                btnColor[7]++;
                btnColor[8]++;
                break;
            case R.id.imBR8:
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
                buttonsC[i].setImageResource(R.drawable.stone);
            } else {
                buttonsC[i].setImageResource(R.drawable.cookie);
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
