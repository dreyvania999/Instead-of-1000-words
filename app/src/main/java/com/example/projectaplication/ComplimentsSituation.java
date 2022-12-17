package com.example.projectaplication;

public class ComplimentsSituation {
    public static String GetComplimentsSituation() {
        String situation = "";
        int rnd = (int) ((Math.random() * ((12 - 1) + 1)) + 1);
        switch (rnd) {
            case 1:
                situation = "Напиши рано утром";
                break;
            case 2:
                situation = "Скажи при первой встрече";
                break;
            case 3:
                situation = "Перед тем как попращаетесь скажи";
                break;
            case 4:
                situation = "Стоит обняться и сказать";
                break;
            case 5:
                situation = "Скажи с улыбкой на едине";
                break;
            case 6:
                situation = "Напиши перед сном";
                break;
            case 7:
                situation = "Скажи на общей прогулке";
                break;
            case 8:
                situation = "Скажи когда будешь дарить подарок";
                break;
            case 9:
                situation = "Скажи это за общим столом";
                break;
            case 10:
                situation = "Скажи когда будете на едине";
                break;
            case 11:
                situation = "Это стоит сказать перед долгим расставанием";
                break;
            case 12:
                situation = "Скажи это если нужно извиниться";
                break;

        }


        return situation;
    }
}
