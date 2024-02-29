import java.awt.color.ICC_ColorSpace;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main2 {
    BufferedReader br;
    char[][] mainMemory = new char[100][4];
    int[] registerR = new int[4];
    char[] registerIR;
    int registerIC;
    boolean registerC;
    int memoryUsed;
    int SI;

    public Main2(String f){
        try{
            File fd = new File(f);
            br = new BufferedReader(new FileReader(fd));
        }catch(Exception e ){System.out.println(e);}
    }
    public void LOAD(){
        String str;
        char[] arr = new char[20];
        try{
            while((str = br.readLine()) != null) {
                arr = str.toCharArray();
                if (arr[0] == '$' && arr[1] == 'A' && arr[2] == 'M' && arr[3] == 'J') {
                    init();
                } else if (arr[0] == '$' && arr[1] == 'D' && arr[2] == 'T' && arr[3] == 'A') {
                    startExecution();
                } else if (arr[0] == '$' && arr[1] == 'E' && arr[2] == 'N' && arr[3] == 'D') {
                    break;
                } else {
                    for (int i = 0; i < str.length(); i++) {
                        if (i % 4 == 0 && i != 0) {
                            memoryUsed++;
                        }
                        mainMemory[memoryUsed][i % 4] = arr[i];
                        System.out.println(mainMemory[memoryUsed][i % 4]);
                    }
                }
            }
        }catch(Exception e){}
    }
    public void init(){
        mainMemory = new char[100][4];
        registerR = new int[4];
        registerIR = new char[4];
        registerC = false;
        registerIC = 0;
        memoryUsed = 0;
        SI = 0;
        System.out.println("Memory Initialised");
    }
    public void startExecution(){
//        IC initialized to [00]
        registerIC = 0;


        while (1<2) {

            if(registerIC==100){
                break;
            }

//        here instruction register 4 bytes will be loaded with instruction GD10 from memory location;
            for (int i = 0; i < 4; i++) {
                registerIR[i] = mainMemory[registerIC][i];
            }
            registerIC++;

            if(registerIR[0] == 'L' && registerIR[1] == 'R')
            {

            }  else if(registerIR[0] == 'S' && registerIR[1] == 'R')
            {

            }  else if(registerIR[0] == 'B' && registerIR[1] == 'T')
            {

            }  else
            if(registerIR[0] == 'C' && registerIR[1] == 'R')
            {

            }  else if (registerIR[0] == 'G' && registerIR[1] == 'D') {
                System.out.println("Reading data");
                SI = 1;
                masterMode();

            } else if (registerIR[0] == 'P' && registerIR[1] == 'D') {
                SI = 2;
                masterMode();
            } else if (registerIR[0] == 'H') {
                SI = 3;
            } else if(registerIR[0]== 'L' && registerIR[1]=='R'){
                registerIR[3]='0';
                int loc = Integer.parseInt(String.valueOf(registerIR[2])+String.valueOf(registerIR[3]));

                String data, total ="";
                char[] charArr;

                for(int i=0;i<10;i++){
                    for (int j=0;j<4;j++){
                        System.out.print(mainMemory[loc][j]);
                    }
                    loc++;
                }


            }
        }
    }
    public void masterMode(){
        switch(SI){
            case 1:
                System.out.println("Master mode read");
                read();
                break;
            case 2:
                System.out.println("Master mode write output");
                write();
                break;
            case 3:
                halt();
                break;
            default:
                SI = 0;
        }
        SI=0;
    }

    void read(){

        registerIR[3]='0';
        int loc = Integer.parseInt(String.valueOf(registerIR[2])+String.valueOf(registerIR[3]));

//        System.out.print("IR Register");
//        System.out.println(String.valueOf(registerIR[2]));

        System.out.println( "Loc: "+loc);

        char[] charArr;
        String str;

        try {
            str = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("inside read"+str.length());
        int len = str.length();

        charArr = str.toCharArray();

        for(int i=0;i<len;i++){

//            System.out.println("inside for");
//            System.out.println(" " +charArr[i] +" "+loc);

            if(i%4==0 && i!=0){
                loc++;
            }

            // data gets loaded into memory at location
            mainMemory[loc][i%4]=charArr[i];
            System.out.print(mainMemory[loc][i%4]);

        }
    }
    void write(){
        registerIR[3]='0';
        int loc = Integer.parseInt(String.valueOf(registerIR[2])+String.valueOf(registerIR[3]));
        System.out.println(loc);

        String data, total ="";
        char[] charArr;

        for(int i=0;i<10;i++){
            for (int j=0;j<4;j++){
                System.out.print(mainMemory[loc][j]);
            }
            loc++;
        }
    }
    void halt(){
        return;
    }
}