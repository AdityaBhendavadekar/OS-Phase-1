//          DESCRIPTION OF INSTRUCTION
// GD10 means read data from data card and putting it into 10th mem location
// PD10(print data) means whatever location is given , take data from that block and print it(here 10th block) (1 block is 10 words)
// LR10(load register) means load register with the contents present in 10th block 
// SR10 (strore register) store contents of regoster in 10th block
// CR20 (compare) contents of register R is compared with mem location 20, if same toggle register(C) set to true
// BT 05 (branch on true) means jump to 05 when C(toggle regsiter) is true
// H means halt


public class Main{
    public static void main(String[] args){
        Main2 obj = new Main2("src/data.txt","src/output.txt");
        obj.LOAD();
    }
}

