/*
*  Compilation:  javac playfair.java
*  Execution:	java playfair mode text keyText
*
*  mode is either encode or decode
*  text can be either plaintext or ciphertext depending on mode
*  keyText stays the same regardless of mode
*
*  This programs converts plaintext to ciphertext or decodes
*  ciphertext using the playfair cipher
*
*  Made by Tanzir Hasan for Cyber Security 10/11/20
*/

public class playfair
{
  private char [] text;
  private String keyText = new String();
  private char [][] cipher = new char [5][5];

  public void playfairMake(String word, String Key){
      text = new char [word.length()];
      keyText = Key.toUpperCase();;
      word = word.toUpperCase();
      for (int i =0; i <keyText.length(); i++){
        cipher[i/5][i%5] = keyText.charAt(i);
      }
      for (int i =0; i <word.length(); i++){
        text[i] = word.charAt(i);
        if (text[i] == 'J'){
          text[i] = 'I';
        }
      }
  }

  public String getKey(){
    return this.keyText;
  }

  public char[] getText (){
    return this.text;
  }

  public void printCipher(){
    for (int i =0; i <keyText.length(); i++){
      System.out.println(cipher[i/5][i%5]);
    }
  }

  public char[] format (){
    //text -> must be even, x between double letters in the same pair
    int [] xpos = new int [text.length/2];
    int numDouble = 0;
    for (int i =0; i <text.length ; i+=2){
      if (i+1 < text.length){
        if (text[i] == text[i+1]){
          xpos[numDouble] = i+1 + numDouble;
          numDouble +=1;
          i--;
        }
      }
    }
    int newlength = text.length+numDouble;
    boolean zend = false;
    if (newlength%2 != 0){
      newlength+=1;
      zend = true;
    }
    char [] newtext = new char [newlength];
    if (zend){
      newtext[newlength-1]= 'Z';
    }
    int numadded = 0;
    for (int i =0; i <newtext.length ; i++){
      if(numDouble!= 0 && numadded != numDouble){
        if (xpos[numadded] == i){
          newtext[i] = 'X';
          i++;
          numadded++;
        }
      }
      if (newtext[i] != 'Z'){
        newtext[i] = text[i-numadded];
        if (newtext[i] == 'J'){
          newtext[i] = 'I';
        }
      }
    }
    return newtext;
  }

  public char [] shift (char flet, char slet, int type){
    boolean flfound = false;
    boolean slfound = false;
    int fx = 0;
    int sx = 0;
    int fy = 0;
    int sy = 0;
    for (int i =0; i < 5 && !flfound; i++){
      for (int j=0; j<5 && !flfound; j++){
        if (cipher[i][j] == flet){
          fy= i;
          fx = j;
          flfound = true;
        }
      }
    }
    for (int i =0; i < 5 && !slfound; i++){
      for (int j=0; j<5 && !slfound; j++){
        if (cipher[i][j] == slet){
          sy= i;
          sx = j;
          slfound = true;
        }
      }
    }
    if (fx == sx){
      return vshift (fx,fy,sx,sy,type);
    }
    else if(fy==sy){
      return hshift(fx,fy,sx,sy,type);
    }
    else{
      return regshift(fx,fy,sx,sy,type);
    }
  }

  public char [] hshift (int fx, int fy, int sx, int sy, int type){
    int change1 = (fy+type)%5;
    int change2 = (sy+type)%5;
    if (change1 < 0){
      change1 += 5;
      change2 += 5;
    }
    char [] anstext ={cipher[change1][fx],cipher[change2][sx]};
    return anstext;
  }

  public char [] vshift (int fx, int fy, int sx, int sy, int type){
    int change1 = (fx+type)%5;
    int change2 = (sx+type)%5;
    if (change1 < 0){
      change1 += 5;
      change2 += 5;
    }
    char [] anstext = {cipher[fy][change1],cipher[sy][change2]};
    return anstext;
  }

  public char [] regshift (int fx, int fy, int sx, int sy, int type){
    char [] anstext = {cipher[fy][sx],cipher[sy][fx]};
    return anstext;
  }

  public String decode(){
    char [] formtext = text;
    for (int i =0; i< formtext.length; i+=2){
      char [] newchars =shift(formtext[i], formtext[i+1], -1);
      formtext[i] = newchars[0];
      formtext[i+1] = newchars[1];
    }
    String ans = new String(formtext);
    return ans;
  }

  public String encode(){
    char[] formtext = format();
    for (int i =0; i< formtext.length; i+=2){
      char [] newchars =shift(formtext[i], formtext[i+1], 1);
      formtext[i] = newchars[0];
      formtext[i+1] = newchars[1];
    }
    String ans = new String(formtext);
    return ans;
  }

  public static void main (String [] args){
    playfair one = new playfair();
    String mode = args[0];
    String regtext = args[1];
    String key = args[2];
    one.playfairMake(regtext, key);
    //one.printCipher();
    if (mode.equals("encode") ){
      System.out.println(one.encode());
    }
    else{
      System.out.println(one.decode());
    }
  }
}
