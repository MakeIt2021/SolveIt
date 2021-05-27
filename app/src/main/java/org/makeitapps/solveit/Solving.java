package org.makeitapps.solveit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.StrictMath.random;

public class Solving<userReturned> extends AppCompatActivity {
    //Для генерации примеров и вариантов
    Button btn1, btn2, btn3, btn4;
    TextView formula, progress, timer;
    String form = "";
    int res = 0;
    int myAnswer1;
    int myAnswer2;
    int myAnswer3;
    int myAnswer4;
    String sum;
    String sub;
    String mult;
    String dev;
    int operation;
    File file = new File("/data/data/com.makeit.solveit/preferences.txt");

    //Для статистики
    boolean wasSum;
    boolean wasSub;
    boolean wasMult;
    boolean wasDev;
    int correct = 0;
    int counter = 0;
    int sumTime = 0;
    int subTime = 0;
    int multTime = 0;
    int devTime = 0;
    int sumCorrect = 0;
    int subCorrect = 0;
    int multCorrect = 0;
    int devCorrect = 0;
    int sumStatsCounter = 0;
    int subStatsCounter = 0;
    int multStatsCounter = 0;
    int devStatsCounter = 0;

    //Для генерации примеров в соответствии с настройками
    int min;
    int max;
    int seconds;
    int amount;

    //Всякие плюшки
    Vibrator vibrator;
    boolean tap;
    CountDownTimer countDownTimer;
    int time;
    int crossTime;
    int tickTime;
    ImageView cross;
    ImageView tick;


    //Play/pause
    ImageButton playPauseBtn;
    ImageButton closeBtn;
    CountDownTimer countDownTimerAfterPause;
    boolean afterPause;
    int couldBePaused;
    boolean userReturned = true;

    //Без варианнтов ответа
    EditText edt;
    View everything;
    ImageButton zenModeBtn;
    boolean zenMode;
    File zenFile = new File("/data/data/com.makeit.solveit/solvingPref.txt");
    TextView timer2;

    //Звуки
    SoundPool correctSound;
    SoundPool incorrectSound;
    private int mySoundId = 1;
    private int myStreamId;
    ImageButton soundBtn;
    File soundFile = new File("/data/data/com.makeit.solveit/sound.txt");
    boolean isSound;




    //===================================================================================================================
    //===================================================================================================================
    //===================================================================================================================

    //Рандомайзер
    public static double getRandomIntegerBetweenRange(int min, int max){
        int x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }

    //Генерация и запись сложения
    boolean createSum() {
        progress.setText(counter + " / " + amount);
        //Последнее действие - сложение
        wasSum = true;
        wasSub = false;
        wasMult = false;
        wasDev = false;

        btn1 = findViewById(R.id.option1);
        btn2 = findViewById(R.id.option2);
        btn3 = findViewById(R.id.option3);
        btn4 = findViewById(R.id.option4);
        int a = (int) getRandomIntegerBetweenRange(min, max);
        int b = (int) getRandomIntegerBetweenRange(min, max);

        form = a + " + " + b;
        res = a + b;

        //Рандомный порядок ответов:
        int answer1 = (int) (random() * 3);
        int answer2 = (int) (random() * 3);
        int answer3 = (int) (random() * 3);
        int answer4 = (int) (random() * 3);

        //Проверка на отсутствие двух одинаковых рандомных чисел порядка ответов
        while (answer1 == answer2 || answer1 == answer3 || answer1 == answer4 || answer2 == answer3 || answer2 == answer4 || answer3 == answer4) {
            answer1 = (int) (random() * 4);
            answer2 = (int) (random() * 4);
            answer3 = (int) (random() * 4);
            answer4 = (int) (random() * 4);
        }

        int variant1 = res; //всегда правилен
        int variant2 = (int) (res + random() * 10 + 1); //рандомное число варианта ответа
        int variant3 = (int) (res - random() * 10 + 1); //рандомное число варианта ответа
        int variant4 = res + 10; //рандомное число варианта ответа


        //Проверка на отсутсвие двух одинаковых рандомных ответов
        while (variant1 == variant2 || variant1 == variant3 || variant1 == variant4 || variant2 == variant3 || variant2 == variant4 || variant3 == variant4) {
            variant2 = (int) (res + random() * 10 + 1);
            variant3 = (int) (res - random() * 10 + 1);
            variant4 = res - 10;
        }

        //массив с ответами в таком порядке: правильный, неправильный, неправильный, неправильный
        int[] array = {variant1, variant2, variant3, variant4};


        myAnswer1 = (array[answer1]);
        myAnswer2 = (array[answer2]);
        myAnswer3 = (array[answer3]);
        myAnswer4 = (array[answer4]);

        formula = findViewById(R.id.formula);
        formula.setText(String.valueOf(form));

        btn1.setText(String.valueOf(myAnswer1));
        btn2.setText(String.valueOf(myAnswer2));
        btn3.setText(String.valueOf(myAnswer3));
        btn4.setText(String.valueOf(myAnswer4));

        if (afterPause)
            countDownTimerAfterPause.onFinish();


        countDownTimer.start();

        afterPause = false;
        createFormerEquation();


        return wasSum;
    }

    //Генерация и запись вычитания
    boolean createSub() {
        progress.setText(counter + " / " + amount);
        //Последнее действие - вычитание
        wasSum = false;
        wasSub = true;
        wasMult = false;
        wasDev = false;
        btn1 = findViewById(R.id.option1);
        btn2 = findViewById(R.id.option2);
        btn3 = findViewById(R.id.option3);
        btn4 = findViewById(R.id.option4);
        int a = (int) getRandomIntegerBetweenRange(min, max);
        int b = (int) getRandomIntegerBetweenRange(min, max);

        while (a - b < 0) {
            a = (int) getRandomIntegerBetweenRange(min, max);
            b = (int) getRandomIntegerBetweenRange(min, max);
        }

        form = a + " - " + b;
        res = a - b;
        //Рандомный порядок ответов:
        int answer1 = (int) (random() * 3);
        int answer2 = (int) (random() * 3);
        int answer3 = (int) (random() * 3);
        int answer4 = (int) (random() * 3);

        //Проверка на отсутствие двух одинаковых рандомных чисел порядка ответов
        while (answer1 == answer2 || answer1 == answer3 || answer1 == answer4 || answer2 == answer3 || answer2 == answer4 || answer3 == answer4) {
            answer1 = (int) (random() * 4);
            answer2 = (int) (random() * 4);
            answer3 = (int) (random() * 4);
            answer4 = (int) (random() * 4);
        }

        int variant1 = res; //всегда правилен
        int variant2 = (int) (res + random() * 10 + 1); //рандомное число варианта ответа
        int variant3 = (int) (res - random() * 10 + 1); //рандомное число варианта ответа
        int variant4 = res - 10; //рандомное число варианта ответа


        //Проверка на отсутсвие двух одинаковых рандомных ответов
        while (variant1 == variant2 || variant1 == variant3 || variant1 == variant4 || variant2 == variant3 || variant2 == variant4 || variant3 == variant4) {
            variant2 = (int) (res + random() * 10 + 1);
            variant3 = (int) (res - random() * 10 + 1);
            variant4 = res + 10;
        }

        //массив с ответами в таком порядке: правильный, неправильный, неправильный, неправильный
        int[] array = {variant1, variant2, variant3, variant4};


        myAnswer1 = (array[answer1]);
        myAnswer2 = (array[answer2]);
        myAnswer3 = (array[answer3]);
        myAnswer4 = (array[answer4]);

        formula = findViewById(R.id.formula);
        formula.setText(String.valueOf(form));

        btn1.setText(String.valueOf(myAnswer1));
        btn2.setText(String.valueOf(myAnswer2));
        btn3.setText(String.valueOf(myAnswer3));
        btn4.setText(String.valueOf(myAnswer4));

        if (afterPause)
            countDownTimerAfterPause.onFinish();

        countDownTimer.start();


        afterPause = false;
        createFormerEquation();

        return wasSub;
    }

    //Генерация и запись умножения
    boolean createMult() {
        progress.setText(counter + " / " + amount);
        //Последнее действие - умножение
        wasSum = false;
        wasSub = false;
        wasMult = true;
        wasDev = false;
        btn1 = findViewById(R.id.option1);
        btn2 = findViewById(R.id.option2);
        btn3 = findViewById(R.id.option3);
        btn4 = findViewById(R.id.option4);
        int a = (int) getRandomIntegerBetweenRange(min, max);
        int b = (int) getRandomIntegerBetweenRange(min, max);

        if (a >= max / 10 && b >= max / 10) {
            b = (int) (Math.random() * 10);
        }

        form = a + " * " + b;
        res = a * b;
        //Рандомный порядок ответов:
        int answer1 = (int) (random() * 3);
        int answer2 = (int) (random() * 3);
        int answer3 = (int) (random() * 3);
        int answer4 = (int) (random() * 3);

        //Проверка на отсутствие двух одинаковых рандомных чисел порядка ответов
        while (answer1 == answer2 || answer1 == answer3 || answer1 == answer4 || answer2 == answer3 || answer2 == answer4 || answer3 == answer4) {
            answer1 = (int) (random() * 4);
            answer2 = (int) (random() * 4);
            answer3 = (int) (random() * 4);
            answer4 = (int) (random() * 4);
        }

        int variant1 = res; //всегда правилен
        int variant2 = (int) (res + random() * 10 + 1); //рандомное число варианта ответа
        int variant3 = (int) (res - random() * 10 + 1); //рандомное число варианта ответа
        int variant4 = res - 10; //рандомное число варианта ответа


        //Проверка на отсутсвие двух одинаковых рандомных ответов
        while (variant1 == variant2 || variant1 == variant3 || variant1 == variant4 || variant2 == variant3 || variant2 == variant4 || variant3 == variant4) {
            variant2 = (int) (res + random() * 10 + 1);
            variant3 = (int) (res - random() * 10 + 1);
            variant4 = res + 10;
        }

        //массив с ответами в таком порядке: правильный, неправильный, неправильный, неправильный
        int[] array = {variant1, variant2, variant3, variant4};


        myAnswer1 = (array[answer1]);
        myAnswer2 = (array[answer2]);
        myAnswer3 = (array[answer3]);
        myAnswer4 = (array[answer4]);

        formula = findViewById(R.id.formula);
        formula.setText(String.valueOf(form));

        btn1.setText(String.valueOf(myAnswer1));
        btn2.setText(String.valueOf(myAnswer2));
        btn3.setText(String.valueOf(myAnswer3));
        btn4.setText(String.valueOf(myAnswer4));

        if (afterPause)
            countDownTimerAfterPause.onFinish();

        countDownTimer.start();

        afterPause = false;
        createFormerEquation();

        return wasMult;
    }

    //Генерация и запись деления
    boolean createDev() {
        progress.setText(counter + " / " + amount);
        //Последнее действие - деление
        wasSum = false;
        wasSub = false;
        wasMult = false;
        wasDev = true;
        btn1 = findViewById(R.id.option1);
        btn2 = findViewById(R.id.option2);
        btn3 = findViewById(R.id.option3);
        btn4 = findViewById(R.id.option4);
        int a = (int) getRandomIntegerBetweenRange(min, max);
        int b = (int) getRandomIntegerBetweenRange(min, max);

        while (a == 1 || b == 0 || b == 1 || a == b || a % b != 0) {
            a = (int) getRandomIntegerBetweenRange(min, max);
            b = (int) getRandomIntegerBetweenRange(min, max);
        }

        form = a + " : " + b;
        res = a / b;
        //Рандомный порядок ответов:
        int answer1 = (int) (random() * 3);
        int answer2 = (int) (random() * 3);
        int answer3 = (int) (random() * 3);
        int answer4 = (int) (random() * 3);

        //Проверка на отсутствие двух одинаковых рандомных чисел порядка ответов
        while (answer1 == answer2 || answer1 == answer3 || answer1 == answer4 || answer2 == answer3 || answer2 == answer4 || answer3 == answer4) {
            answer1 = (int) (random() * 4);
            answer2 = (int) (random() * 4);
            answer3 = (int) (random() * 4);
            answer4 = (int) (random() * 4);
        }

        int variant1 = res; //всегда правилен
        int variant2 = (int) (res + random() * 10 + 1); //рандомное число варианта ответа
        int variant3 = (int) (res - random() * 10 + 1); //рандомное число варианта ответа
        int variant4 = res - 10; //рандомное число варианта ответа


        //Проверка на отсутсвие двух одинаковых рандомных ответов
        while (variant1 == variant2 || variant1 == variant3 || variant1 == variant4 || variant2 == variant3 || variant2 == variant4 || variant3 == variant4) {
            variant2 = (int) (res + random() * 10 + 1);
            variant3 = (int) (res - random() * 10 + 1);
            variant4 = res + 10;
        }

        //массив с ответами в таком порядке: правильный, неправильный, неправильный, неправильный
        int[] array = {variant1, variant2, variant3, variant4};


        myAnswer1 = (array[answer1]);
        myAnswer2 = (array[answer2]);
        myAnswer3 = (array[answer3]);
        myAnswer4 = (array[answer4]);

        formula = findViewById(R.id.formula);
        formula.setText(String.valueOf(form));

        btn1.setText(String.valueOf(myAnswer1));
        btn2.setText(String.valueOf(myAnswer2));
        btn3.setText(String.valueOf(myAnswer3));
        btn4.setText(String.valueOf(myAnswer4));

        if (afterPause)
            countDownTimerAfterPause.onFinish();

        countDownTimer.start();

        afterPause = false;
        createFormerEquation();

        return wasDev;
    }

    void createFormerEquation() {
        formula.setText(String.valueOf(form));
        if (!zenMode) {
            btn1.setText(String.valueOf(myAnswer1));
            btn2.setText(String.valueOf(myAnswer2));
            btn3.setText(String.valueOf(myAnswer3));
            btn4.setText(String.valueOf(myAnswer4));

            btn1.setClickable(true);
            btn2.setClickable(true);
            btn3.setClickable(true);
            btn4.setClickable(true);
        }

        if (zenMode) {
            btn1.setVisibility(View.INVISIBLE);
            btn2.setVisibility(View.INVISIBLE);
            btn3.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.INVISIBLE);

            btn1.setClickable(false);
            btn2.setClickable(false);
            btn3.setClickable(false);
            btn4.setClickable(false);


            countDownTimer.start();
        }
    }


    ///////////////////////////////////НАСТРОЙКИ///////////////////////////////////////////
    void choose() {
        //ВСЕ
        try {
            if (sum.equals("true") && sub.equals("true") && mult.equals("true") && dev.equals("true")) {
                operation = (int) (Math.random() * 4);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createSub();
                }
                if (operation == 2) {
                    createMult();
                }
                if (operation == 3) {
                    createDev();
                }
            }
            //Нет суммы
            if (sum.equals("false") && sub.equals("true") && mult.equals("true") && dev.equals("true")) {
                operation = (int) (Math.random() * 3);
                if (operation == 0) {
                    createSub();
                }
                if (operation == 1) {
                    createMult();
                }
                if (operation == 2) {
                    createDev();
                }
            }
            //Нет разности
            if (sum.equals("true") && sub.equals("false") && mult.equals("true") && dev.equals("true")) {
                operation = (int) (Math.random() * 3);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createMult();
                }
                if (operation == 2) {
                    createDev();
                }
            }
            //Нет произведения
            if (sum.equals("true") && sub.equals("true") && mult.equals("false") && dev.equals("true")) {
                operation = (int) (Math.random() * 3);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createSub();
                }
                if (operation == 2) {
                    createDev();
                }
            }
            //Нет частного
            if (sum.equals("true") && sub.equals("true") && mult.equals("true") && dev.equals("false")) {
                operation = (int) (Math.random() * 3);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createSub();
                }
                if (operation == 2) {
                    createDev();
                }
            }
            //Нет суммы и разности
            if (sum.equals("false") && sub.equals("false") && mult.equals("true") && dev.equals("true")) {
                operation = (int) (Math.random() * 2);
                if (operation == 0) {
                    createMult();
                }
                if (operation == 1) {
                    createDev();
                }
            }
            //Нет суммы и произведения
            if (sum.equals("false") && sub.equals("true") && mult.equals("false") && dev.equals("true")) {
                operation = (int) (Math.random() * 2);
                if (operation == 0) {
                    createSub();
                }
                if (operation == 1) {
                    createDev();
                }
            }
            //Нет суммы и частного
            if (sum.equals("false") && sub.equals("true") && mult.equals("true") && dev.equals("false")) {
                operation = (int) (Math.random() * 2);
                if (operation == 0) {
                    createMult();
                }
                if (operation == 1) {
                    createDev();
                }
            }
            //Нет разности и произведения
            if (sum.equals("true") && sub.equals("false") && mult.equals("false") && dev.equals("true")) {
                operation = (int) (Math.random() * 2);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createDev();
                }
            }
            //Нет разности и частного
            if (sum.equals("true") && sub.equals("false") && mult.equals("true") && dev.equals("false")) {
                operation = (int) (Math.random() * 2);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createMult();
                }
            }
            //Нет произведения и частного
            if (sum.equals("true") && sub.equals("true") && mult.equals("false") && dev.equals("false")) {
                operation = (int) (Math.random() * 2);
                if (operation == 0) {
                    createSum();
                }
                if (operation == 1) {
                    createSub();
                }
            }
            //Только сумма
            if (sum.equals("true") && sub.equals("false") && mult.equals("false") && dev.equals("false")) {
                createSum();
            }
            //Только разность
            if (sum.equals("false") && sub.equals("true") && mult.equals("false") && dev.equals("false")) {
                createSub();
            }
            //Только произведение
            if (sum.equals("false") && sub.equals("false") && mult.equals("true") && dev.equals("false")) {
                createMult();
            }
            //Только частное
            if (sum.equals("false") && sub.equals("false") && mult.equals("false") && dev.equals("true")) {
                createDev();
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //Проверка времени
    void setTime() {
        if (wasSum) {
            sumTime = sumTime + (seconds - time);
        }
        if (wasSub) {
            subTime = subTime + (seconds - time);
        }
        if (wasMult) {
            multTime = multTime + (seconds - time);
        }
        if (wasDev) {
            devTime = devTime + (seconds - time);
        }
    }

    //Проверка ответа на правильность
    void check() {
        choose();
        tap = false;
        if (!zenMode) {
            if (myAnswer1 == res) {
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correct();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
            }
            if (myAnswer2 == res) {
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correct();
                    }
                });
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
            }
            if (myAnswer3 == res) {
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correct();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
            }
            if (myAnswer4 == res) {
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correct();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        incorrect();
                    }
                });
            }
        }
    }

    //Если правилен
    void correct() {
        tick.setImageResource(R.drawable.checkbox_enabled);
        CountDownTimer tickTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tickTime = (int) (millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                this.cancel();
                if (tickTime == 0) {
                    tick.setImageResource(R.drawable.checkbox_enabled_grey);
                }
            }
        };
        tickTimer.start();

        if (isSound) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = 200;
            float leftVolume = curVolume / maxVolume;
            float rightVolume = curVolume / maxVolume;
            int priority = 1;
            int no_loop = 0;
            float normal_playback_rate = 1f;
            myStreamId = correctSound.play(mySoundId, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
        }

        countOperations();
        tap = true;
        countDownTimer.onFinish();
        setTime();
        correct++;
        //Подсчет правильных примеров по действиям
        if (wasSum) {
            sumCorrect++;
        }
        if (wasSub) {
            subCorrect++;
        }
        if (wasMult) {
            multCorrect++;
        }
        if (wasDev) {
            devCorrect++;
        }
        if (counter < amount) {
            check();
        } else {
            sendData();
        }
    }

    //Если неправилен
    void incorrect() {
        cross.setImageResource(R.drawable.checkbox_disabled);
        CountDownTimer tickTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                crossTime = (int) (millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                this.cancel();
                if (crossTime == 0) {
                    cross.setImageResource(R.drawable.checkbox_disabled_grey);
                }
            }
        };
        tickTimer.start();

        if (isSound) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float leftVolume = curVolume / maxVolume;
            float rightVolume = curVolume / maxVolume;
            int priority = 1;
            int no_loop = 0;
            float normal_playback_rate = 1f;
            myStreamId = incorrectSound.play(mySoundId, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
        }

        long mills = 100L;
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mills);
            vibrator.cancel();
        }
        countOperations();
        tap = true;
        countDownTimer.onFinish();
        if (counter < amount) {
            check();
        } else {
            sendData();
        }
    }

    //Подсчёт опреаций
    void countOperations() {
        if (wasSum) {
            sumStatsCounter++;
        }
        if (wasSub) {
            subStatsCounter++;
        }
        if (wasMult) {
            multStatsCounter++;
        }
        if (wasDev) {
            devStatsCounter++;
        }
    }

    //Передача статистики в Stats.java
    void sendData() {
        Intent intentStats = new Intent(this, Stats.class);
        intentStats.putExtra("correctAmount", correct);
        intentStats.putExtra("amount", amount);
        intentStats.putExtra("time", sumTime + subTime + multTime + devTime);
        intentStats.putExtra("sumTimeTransp", sumTime);
        intentStats.putExtra("subTimeTransp", subTime);
        intentStats.putExtra("multTimeTransp", multTime);
        intentStats.putExtra("devTimeTransp", devTime);
        intentStats.putExtra("sumCorrectTransp", sumCorrect);
        intentStats.putExtra("subCorrectTransp", subCorrect);
        intentStats.putExtra("multCorrectTransp", multCorrect);
        intentStats.putExtra("devCorrectTransp", devCorrect);
        intentStats.putExtra("sumCounterTransp", sumStatsCounter);
        intentStats.putExtra("subCounterTransp", subStatsCounter);
        intentStats.putExtra("multCounterTransp", multStatsCounter);
        intentStats.putExtra("devCounterTransp", devStatsCounter);


        startActivity(intentStats);
        finish();
    }

    void pause() {
        if (afterPause)
            countDownTimerAfterPause.cancel();
        if (couldBePaused % 2 == 0) {
            countDownTimer.cancel();
            couldBePaused++;
            playPauseBtn.setImageResource(R.drawable.play);
            formula.setTextColor(Color.parseColor("#757575"));
            btn1.setTextColor(Color.parseColor("#757575"));
            btn2.setTextColor(Color.parseColor("#757575"));
            btn3.setTextColor(Color.parseColor("#757575"));
            btn4.setTextColor(Color.parseColor("#757575"));
            timer.setTextColor(Color.parseColor("#757575"));
            progress.setTextColor(Color.parseColor("#757575"));
            timer.setTextColor(Color.parseColor("#757575"));
            timer2.setTextColor(Color.parseColor("#757575"));

            btn1.setClickable(false);
            btn2.setClickable(false);
            btn3.setClickable(false);
            btn4.setClickable(false);

            zenModeBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);

            soundBtn.setClickable(true);
            soundBtn.setVisibility(View.VISIBLE);


            close();
            zenMode();
            sound();
        } else {
            countDownTimerAfterPause = new CountDownTimer(time * 1000, 1000)  {
                @Override
                public void onTick(long millisUntilFinished) {
                    time = (int) (millisUntilFinished / 1000);
                    timer.setText(String.valueOf(time));
                    timer2.setText(String.valueOf(time));
                }

                @Override
                public void onFinish() {
                    this.cancel();
                    if (!tap && time == 0)
                        incorrect();
                    else
                        counter++;
                }
            };
            countDownTimer.cancel();
            countDownTimerAfterPause.start();

            afterPause = true;

            couldBePaused++;
            playPauseBtn.setImageResource(R.drawable.pause);

            formula.setTextColor(Color.parseColor("#ffffff"));
            btn1.setTextColor(Color.parseColor("#ffffff"));
            btn2.setTextColor(Color.parseColor("#ffffff"));
            btn3.setTextColor(Color.parseColor("#ffffff"));
            btn4.setTextColor(Color.parseColor("#ffffff"));
            timer.setTextColor(Color.parseColor("#ffffff"));
            progress.setTextColor(Color.parseColor("#ffffff"));
            timer.setTextColor(Color.parseColor("#ffffff"));
            timer2.setTextColor(Color.parseColor("#ffffff"));


            if (!zenMode) {
                btn1.setClickable(true);
                btn2.setClickable(true);
                btn3.setClickable(true);
                btn4.setClickable(true);
            }


            closeBtn.setClickable(false);
            closeBtn.setVisibility(View.INVISIBLE);

            zenModeBtn.setClickable(false);
            zenModeBtn.setVisibility(View.INVISIBLE);

            soundBtn.setClickable(false);
            soundBtn.setVisibility(View.INVISIBLE);

            userReturned = true;
            zen();
            createFormerEquation();

        }
    }

    void close() {
        closeBtn.setClickable(true);
        closeBtn.setImageResource(R.drawable.close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBtn.setImageResource(R.drawable.close_red);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
    }

    //Звук
    void sound() {
        soundBtn.setClickable(true);
        if (isSound) {
            soundBtn.setImageResource(R.drawable.sound_on);
        } else {
            soundBtn.setImageResource(R.drawable.sound_off);
        }

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSound) {
                    soundBtn.setImageResource(R.drawable.sound_off);
                    isSound = false;
                } else {
                    soundBtn.setImageResource(R.drawable.sound_on);
                    isSound = true;
                }
                try {
                    PrintWriter pw = new PrintWriter(soundFile);
                    pw.println(isSound);
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    void zenMode() {
        zenModeBtn.setClickable(true);
        if (zenMode) {
            zenModeBtn.setImageResource(R.drawable.zen);
        } else {
            zenModeBtn.setImageResource(R.drawable.zen_inactive);
        }
        zenModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zenMode) {
                    zenMode = false;
                    zenModeBtn.setImageResource(R.drawable.restart);
                    try {
                        PrintWriter pw = new PrintWriter(zenFile);
                        pw.println(zenMode);
                        pw.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    zenModeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                } else {
                    zenMode = true;
                    zenModeBtn.setImageResource(R.drawable.zen);
                    zenModeBtn.setImageResource(R.drawable.restart);
                    try {
                        PrintWriter pw = new PrintWriter(zenFile);
                        pw.println(zenMode);
                        pw.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    zenModeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }

                try {
                    PrintWriter pw = new PrintWriter(zenFile);
                    pw.println(zenMode);
                    pw.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        pause();
    }


    @Override
    protected void onUserLeaveHint() {
        if (userReturned)
            pause();
        userReturned = false;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solving);
        progress = findViewById(R.id.correctAnswers);
        formula = findViewById(R.id.formula);
        timer = findViewById(R.id.timer);
        btn1 = findViewById(R.id.option1);
        btn2 = findViewById(R.id.option2);
        btn3 = findViewById(R.id.option3);
        btn4 = findViewById(R.id.option4);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        playPauseBtn = findViewById(R.id.playPauseBtn);
        closeBtn = findViewById(R.id.closeBtn);
        everything = findViewById(R.id.everything);
        zenModeBtn = findViewById(R.id.zenModeBtn);
        cross = findViewById(R.id.cross);
        tick = findViewById(R.id.tick);
        edt = findViewById(R.id.edt);
        timer2 = findViewById(R.id.timer2);
        soundBtn = findViewById(R.id.soundBtn);

        zenModeBtn.setVisibility(View.INVISIBLE);
        closeBtn.setVisibility(View.INVISIBLE);
        closeBtn.setClickable(false);

        soundBtn.setClickable(false);
        soundBtn.setVisibility(View.INVISIBLE);

        zen();

        soundBtn.setClickable(false);

        correctSound = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        correctSound.load(this, R.raw.correct, 1);

        incorrectSound = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        incorrectSound.load(this, R.raw.incorrect, 1);


        //Чтение данных из файла
        ArrayList arrayList = arrayList = new ArrayList();
        try {
            Scanner getter = new Scanner(file);
            while (getter.hasNext()) {
                arrayList.add(getter.next());
            }
            sum = (String) arrayList.get(0);
            sub = (String) arrayList.get(1);
            mult = (String) arrayList.get(2);
            dev = (String) arrayList.get(3);
            min = Integer.parseInt((String) arrayList.get(4));
            max = Integer.parseInt((String) arrayList.get(5));
            seconds = Integer.parseInt((String) arrayList.get(6));
            amount = Integer.parseInt((String) arrayList.get(7));


            getter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }

        // Пауза
        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        //Звук
        try {
            Scanner getSoundPref = new Scanner(soundFile);
            try {
                isSound = Boolean.parseBoolean(getSoundPref.next());
            } catch (NoSuchElementException e) {
                isSound = false;
                PrintWriter pw = new PrintWriter(soundFile);
                pw.println(isSound);
                pw.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //ZEN_MODE
        try {
            Scanner getZenPref = new Scanner(zenFile);
            try {
                zenMode = Boolean.parseBoolean(getZenPref.next());
            } catch (NoSuchElementException e) {
                zenMode = false;
                PrintWriter pw = new PrintWriter(zenFile);
                pw.println(zenMode);
                pw.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //Таймер
        couldBePaused = 0;
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = (int) (millisUntilFinished / 1000);
                if (!zenMode) {
                    timer.setText(String.valueOf(time));
                    timer2.setVisibility(View.INVISIBLE);
                }
                else {
                    if (!afterPause)
                        timer2.setText(String.valueOf(time));
                    timer.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                this.cancel();
                if (!tap)
                    incorrect();
                else
                    counter++;
            }
        };
        check();
        createFormerEquation();

        if (!zenMode) {
            edt.setFocusable(false);
            edt.setVisibility(View.INVISIBLE);
        } else {
            edt.setFocusable(true);
            edt.setVisibility(View.VISIBLE);
        }


        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                zen();
                return true;
            }
        });
    }

    void zen() {
        try {
            if (Integer.parseInt(String.valueOf(edt.getText())) == res) {
                correct();
                edt.setText("");
                edt.setFocusableInTouchMode(true); edt.requestFocus();
            }

            else {
                edt.setText("");
                incorrect();
            }
        } catch (NumberFormatException e) {

        }

    }
}