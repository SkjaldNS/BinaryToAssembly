public class Binary {

    static boolean validateBinary(String[] binaryArr) {
        for (String s : binaryArr) {
            if (s.length() != 16) {
                return false;
            }
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) != '0' && s.charAt(j) != '1') {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean validateBinary(String binary) {
        if (binary.length() != 16) {
            return false;
        }
        for (int j = 0; j < binary.length(); j++) {
            if (binary.charAt(j) != '0' && binary.charAt(j) != '1') {
                return false;
            }
        }
        return true;
    }

    static String extractSubString(String s, int start, int end) {
        return s.substring(start, end+1);
    }

    static String convertBinaryToAssembly(String binary) {
        String result;
        String subString = extractSubString(binary, 0, 3);
        switch(subString) {
            case "0001" : {
                result = "ADD";
                if(isImmediate(binary)) {
                    result += " DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " SR1: " + convertBinaryToRegister(extractSubString(binary, 7, 9)) + " IMM5: " + convertBinaryToOffset(extractSubString(binary, 11, 15));
                }
                else if (!isImmediate(binary) && isExpected(binary, 10, 12, 0)){
                    result += " DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " SR1: " + convertBinaryToRegister(extractSubString(binary, 7, 9)) + " SR2: " + convertBinaryToRegister(extractSubString(binary, 13, 15));
                }
                else {
                    result = "INVALID ADD";
                }
                break;
            }
            case "0101" : {
                result = "AND";
                if(isImmediate(binary)) {
                    result += " DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " SR1: " + convertBinaryToRegister(extractSubString(binary, 7, 9)) + " IMM5: " + convertBinaryToOffset(extractSubString(binary, 11, 15));
                }
                else if (!isImmediate(binary) && isExpected(binary,11,12,0)){
                    result += " DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " SR1: " + convertBinaryToRegister(extractSubString(binary, 7, 9)) + " SR2: " + convertBinaryToRegister(extractSubString(binary, 13, 15));
                }
                else {
                    result = "INVALID AND";
                }
                break;
            }
            case "0000" : {
                result = "BR"+ convertBinaryToNZP(extractSubString(binary, 4, 6)) + " PCoffset9: " + convertBinaryToOffset(extractSubString(binary, 7, 15));;
                break;
            }
            case "1100" : {
                if (isExpected(binary, 4,6,0) && isExpected(binary, 10,15,0)) {
                    result = "JMP BaseR: " + convertBinaryToRegister(extractSubString(binary, 7, 9));
                }
                else if (isExpected(binary, 4,6,1) && isExpected(binary, 10,15,1)) {
                    result = "RET";
                }
                else {
                    result = "INVALID JMP/RET";
                }
                break;
            }
            case "0100" : {
                if (binary.charAt(4) == '0') {
                    result = "JSR PCoffset11: " + convertBinaryToOffset(extractSubString(binary, 5, 15));
                }
                else if (isExpected(binary,4,6,0) && isExpected(binary,10,15,0)) {
                    result = "JSRR BaseR: " + convertBinaryToRegister(extractSubString(binary, 7, 9));
                }
                else {
                    result = "INVALID JSR/JSRR";
                }
                break;
            }
            case "0010" : {
                result = "LD DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " PCoffset9: " + convertBinaryToOffset(extractSubString(binary, 7, 15));
                break;
            }
            case "1010" : {
                result = "LDI DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " PCoffset9: " + convertBinaryToOffset(extractSubString(binary, 7, 15));
                break;
            }
            case "0110" : {
                result = "LDR DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " BaseR: " + convertBinaryToRegister(extractSubString(binary, 7, 9)) + " offset6: " + convertBinaryToOffset(extractSubString(binary, 10, 15));
                break;
            }
            case "1110" : {
                result = "LEA DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " PCoffset9: " + convertBinaryToOffset(extractSubString(binary, 7, 15));
                break;
            }
            case "1001" : {
                if(isExpected(binary,10,15,1)){
                    result = "NOT DR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " SR: " + convertBinaryToRegister(extractSubString(binary, 7, 9));
                }
                else {
                    System.out.println(binary.charAt(10));
                    result = "INVALID NOT";
                }
                break;
            }
            case "1000" : {
                if(isExpected(binary,4,15,0)){
                    result = "RTI";
                }
                else {
                    result = "INVALID RTI";
                }
                break;
            }
            case "0011" : {
                result = "ST SR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " PCoffset9: " + convertBinaryToOffset(extractSubString(binary, 7, 15));
                break;
            }
            case "1011" : {
                result = "STI SR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " PCoffset9: " + convertBinaryToOffset(extractSubString(binary, 7, 15));
                break;
            }
            case "0111" : {
                result = "STR SR: " + convertBinaryToRegister(extractSubString(binary, 4, 6)) + " BaseR: " + convertBinaryToRegister(extractSubString(binary, 7, 9)) + " offset6: " + convertBinaryToOffset(extractSubString(binary, 10, 15));
                break;
            }
            case "1111" : {
                if(isExpected(binary,4,7,0)){
                    result = "TRAP " + convertBinaryToHexadecimal(extractSubString(binary, 8, 15));
                }
                else {
                    result = "INVALID TRAP";
                }
                break;
            }
            case "1101" : {
                result = "RESERVED";
                break;
            }
            default: {
                result = "INVALID";
                break;
            }

        }
        return result;
    }

    static String convertBinaryToRegister(String binary) {
        String result;
        switch(binary) {
            case "000" : {
                result = "R0";
                break;
            }
            case "001" : {
                result = "R1";
                break;
            }
            case "010" : {
                result = "R2";
                break;
            }
            case "011" : {
                result = "R3";
                break;
            }
            case "100" : {
                result = "R4";
                break;
            }
            case "101" : {
                result = "R5";
                break;
            }
            case "110" : {
                result = "R6";
                break;
            }
            case "111" : {
                result = "R7";
                break;
            }
            default: {
                result = "INVALID";
                break;
            }
        }
        return result;
    }

    static String convertBinaryToNZP(String binary) {
        String result;
        switch(binary) {
            case "000" : {
                result = "NEXT";
                break;
            }
            case "001" : {
                result = "p";
                break;
            }
            case "010" : {
                result = "z";
                break;
            }
            case "011" : {
                result = "zp";
                break;
            }
            case "100" : {
                result = "n";
                break;
            }
            case "101" : {
                result = "np";
                break;
            }
            case "110" : {
                result = "nz";
                break;
            }
            case "111" : {
                result = "nzp";
                break;
            }
            default: {
                result = "INVALID";
                break;
            }
        }
        return result;
    }

    static String convertBinaryToOffset(String binary) {
        int value = 0;
        if (binary.charAt(0) == '1') {
            value = (int) (-Math.pow(2, (binary.length()-1)));
        }
        if(binary.charAt((binary.length()-1)) == '1') {
            value += 1;

        }
        for (int i = binary.length()-2; i > 0; i--) {
            if (binary.charAt(i) == '1') {
                value += (int) Math.pow(2, binary.length()-i-1);
            }
        }
        return Integer.toString(value);
    }

    static String convertBinaryToHexadecimal(String binary) {
        int value = 0;
        for (int i = binary.length()-1; i >= 0; i--) {
            if (binary.charAt(i) == '1') {
                value += (int) Math.pow(2, (binary.length() - i - 1));
            }
        }
        return "x" + Integer.toHexString(value);
    }

    static boolean isImmediate(String binary) {
        return binary.charAt(10) == '1';
    }

    static boolean isExpected(String binary, int start, int end, int value) {
        String evaluate = binary.substring(start, end+1);
        for (int i = 0; i < evaluate.length(); i++) {
            if (evaluate.charAt(i) != Integer.toString(value).charAt(0)) {
                return false;
            }
        }
        return true;
    }
}
