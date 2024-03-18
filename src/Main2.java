import java.awt.color.ICC_ColorSpace;
import java.io.*;

public class Main2 {
    BufferedReader br;
    FileWriter writer;
    char[][] mainMemory = new char[100][4];
    char[] registerR = new char[4];
    char[] registerIR;
    int registerIC;
    boolean registerC;
    int memoryUsed;
    int SI;
    public Main2(String input, String output){
        try{
            File fd = new File(input);
            br = new BufferedReader(new FileReader(fd));
            writer = new FileWriter(output);
        }catch(Exception e ){System.out.println(e);}
    }
    public void LOAD(){
        String str;
        char[] arr = new char[20];
        try{
            while((str = br.readLine())!=null) {
                arr = str.toCharArray();
                if (arr[0] == '$' && arr[1] == 'A' && arr[2] == 'M' && arr[3] == 'J') {
                    System.out.println("Program card detected.");
                    init();
                } else if (arr[0] == '$' && arr[1] == 'D' && arr[2] == 'T' && arr[3] == 'A') {
                    System.out.println("Data card detected.");
                    startExecution();

                } else if (arr[0] == '$' && arr[1] == 'E' && arr[2] == 'N' && arr[3] == 'D') {
                    System.out.println("\nEnd card.");
                    writer.write("\n");
                }else {
                    if ((memoryUsed == 100)) {
                        System.out.println("No more memory.");
                    }
                    for (int i = 0; i < str.length(); i++) {
                        if (i % 4 == 0 && i != 0) {
                            memoryUsed++;
                        }
                        mainMemory[memoryUsed][i % 4] = arr[i];
                    }
                }
            }
        }catch(Exception e){
            System.out.println("jai ho");
        }
    }
    public void init(){
        mainMemory = new char[100][4];
        registerR = new char[4];
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
                System.out.println("instruction counter overflow");
                break;
            }

//        here instruction register 4 bytes will be loaded with instruction GD10 from memory location;
            for (int i = 0; i < 4; i++) {
                registerIR[i] = mainMemory[registerIC][i];
            }
            registerIC++;

            if(registerIR[0] == 'L' && registerIR[1] == 'R'){
                int loc = Integer.parseInt(String.valueOf(registerIR[2])+String.valueOf(registerIR[3]));
                registerR[0]=mainMemory[loc][0];
                registerR[1] =mainMemory[loc][1];
                registerR[2] =mainMemory[loc][2];
                registerR[3]=mainMemory[loc][3];
            }
            else if(registerIR[0] == 'S' && registerIR[1] == 'R'){
                int loc = Integer.parseInt(String.valueOf(registerIR[2])+String.valueOf(registerIR[3]));
                mainMemory[loc][0]=registerR[0];
                mainMemory[loc][1] =registerR[1];
                mainMemory[loc][2] =registerR[2];
                mainMemory[loc][3]=registerR[3];
            }
            else if(registerIR[0] == 'B' && registerIR[1] == 'T'){
                int loc = Integer.parseInt(String.valueOf(registerIR[2]) + String.valueOf(registerIR[3]));
                if(registerC == true){
                    registerIC=loc;
                    registerC=false;
                }
            }
            else if (registerIR[0] == 'C' && registerIR[1] == 'R') {
                int loc = Integer.parseInt(String.valueOf(registerIR[2]) + String.valueOf(registerIR[3]));
                if (mainMemory[loc][0] == registerR[0] && mainMemory[loc][1] == registerR[1] && mainMemory[loc][2] == registerR[2] && mainMemory[loc][3] == registerR[3]) {
                    registerC = true;
                } else {
                    registerC = false;
                }
            } else if (registerIR[0] == 'G' && registerIR[1] == 'D') {
                    System.out.println("Reading data");
                    SI = 1;
                    masterMode();
            } else if (registerIR[0] == 'P' && registerIR[1] == 'D') {
                    SI = 2;
                    masterMode();
            } else if (registerIR[0] == 'H') {
                SI = 3;
                    break;
                }
            }
        }

    public void masterMode(){
        switch(SI){
            case 1:
                read();
                break;
            case 2:
                try {
                    writeData();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
        char[] charArr;
        String str;
        try {
            str = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int len = str.length();
        charArr = str.toCharArray();
        for(int i=0;i<len;i++){
            if(i%4==0 && i!=0){
                loc++;
            }
            mainMemory[loc][i%4]=charArr[i];
        }
    }
    void writeData() throws IOException {
        registerIR[3]='0';
        String str = new String();
        int loc = Integer.parseInt(String.valueOf(registerIR[2])+String.valueOf(registerIR[3]));
        String data, total ="";
        char[] charArr;
        for(int i=0;i<10;i++){
            str += new String(mainMemory[loc+i]);
            str = str.trim();
            if(!str.isEmpty()){
              total=  total.concat(str);
            }
        }
        System.out.println(""+str);
        writer.write(str+"\n");
        writer.flush();
    }
    void halt(){
        return;
    }
    public void print_memory(){
        for(int i=0;i<100;i++) {
            System.out.println("memory["+i+"] "+new String(mainMemory[i]));
        }
    }
}