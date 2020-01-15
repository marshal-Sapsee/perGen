import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;

public class Main {
    private static Integer amNods, randFunc, randSymb, randNode;
    private static Double amMist;
    private static Faker faker;
    private static char oldChr;
    private static String alphaNumericString, temp, locale, alphabet, numbers, apts, telephone;
    private static StringBuffer sb;
    private static String[] ar;
    static FakeValuesService fakeValuesService;
    static char[] arrSymb;

    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        amNods = Integer.parseInt(args[0]);
        locale = args[1];
        amMist = Double.valueOf(args[2]);
        numbers = "[1-9]{1}";

        if (args[1].equals("be_BY")) {locale = "by"; alphabet = "[а-я]{1}"; apts = "кв.";}
        if (args[1].equals("ru_RU")) {locale = "ru"; alphabet = "[а-я]{1}";apts = "кв.";}
        if (args[1].equals("en_US")) {locale = "en-US"; alphabet = "[a-z]{1}";apts = "apts.";}

        fakeValuesService = new FakeValuesService(new Locale(locale), new RandomService());
        faker = new Faker(new Locale(locale));
        for(int i=0; i<amNods; i++) {GetNode();}
        long timeSpent = System.currentTimeMillis() - startTime;

        System.out.println("Программа выполнялась " + timeSpent/1000 + " секунд");
    }

    public static void GetNode () {
        if (locale.equals("en-US")) {telephone = faker.bothify("+1(###)###-####");}
        else {telephone = faker.phoneNumber().phoneNumber();}
        ar = new String[]{faker.name().fullName(), faker.address().cityName(), faker.address().streetName(), faker.bothify("##"), (faker.bothify("##")), faker.address().zipCode(), telephone};

        for (int i=0; i<amMist; i++){
            randNode = (int) ((Math.random())*ar.length);
            randFunc = (int) (Math.random()*3);
            randSymb = (int) (Math.random()*ar[randNode].length());

            switch (randFunc){
                case 0: {replace(); break;}
                case 1: {add(); break;}
                case 2: {delete(); break;}
            }
        }

        System.out.println(String.format("%s, %s, %s, %s, %s %s, %s, %s; ", ar[0], ar[1], ar[2], ar[3], apts, ar[4], ar[5], ar[6]));
    }

    public static void replace(){
        try
        {
            if (ar[randNode].length()>1 && randSymb == ar[randNode].length()) {
                arrSymb = ar[randNode].toCharArray();
                oldChr = arrSymb[randSymb];
                arrSymb[randSymb] = arrSymb[randSymb - 1];
                arrSymb[randSymb-1] = oldChr;
            }
            else if (ar[randNode].length()>1 && randSymb==0) {
                arrSymb = ar[randNode].toCharArray();
                oldChr = arrSymb[randSymb];
                arrSymb[randSymb] = arrSymb[randSymb + 1];
                arrSymb[randSymb+1] = oldChr;
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {System.out.println(e);}
    }

    public static void add(){
        temp = ar[randNode];
        sb = new StringBuffer(temp);

        switch (randNode){
            case 0:alphaNumericString = fakeValuesService.regexify(alphabet);; break;
            case 1:alphaNumericString = fakeValuesService.regexify(alphabet);; break;
            case 2:alphaNumericString = fakeValuesService.regexify(alphabet);; break;
            case 3:alphaNumericString = fakeValuesService.regexify(numbers);; break;
            case 4:alphaNumericString = fakeValuesService.regexify(numbers);; break;
            case 5:alphaNumericString = fakeValuesService.regexify(numbers);; break;
            case 6:alphaNumericString = fakeValuesService.regexify(numbers);; break;
        }

        if(locale.equals("by")){
            temp.replace("и", "і");
            temp.replace("у", "ў");
            ar[randNode] = temp;
        }
        sb.insert(randSymb, faker.bothify(alphaNumericString));
        temp = String.valueOf(sb);

        ar[randNode] = temp;
    }

    public static void delete(){
        arrSymb = ar[randNode].toCharArray();
        if(arrSymb.length>1) {
            temp = ar[randNode];
            sb = new StringBuffer(temp);
            sb.deleteCharAt(randSymb);
            temp = String.valueOf(sb);
            ar[randNode] = temp;
        }
        else {add();}
    }
}