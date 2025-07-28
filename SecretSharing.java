import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class SecretSharing {
    public static void main(String[] args) throws Exception {
        String file1 = "testcase1.json";
        String file2 = "testcase2.json";

        Map<Integer, BigInteger> points1 = parseJsonFile(file1);
        Map<Integer, BigInteger> points2 = parseJsonFile(file2);

        BigInteger secret1 = lagrangeInterpolation(points1);
        BigInteger secret2 = lagrangeInterpolation(points2);

        System.out.println("Secret from Testcase 1: " + secret1);
        System.out.println("Secret from Testcase 2: " + secret2);
    }

    static Map<Integer, BigInteger> parseJsonFile(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        Map<Integer, BigInteger> map = new LinkedHashMap<>();
        int k = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.contains("\"k\"")) {
                k = Integer.parseInt(line.replaceAll("[^0-9]", ""));
            } else if (line.matches("^\\s*\"\\d+\":\\s*\\{")) {
                int x = Integer.parseInt(line.trim().split(":")[0].replaceAll("\"", ""));
                String baseLine = sc.nextLine().trim();
                int base = Integer.parseInt(baseLine.split(":")[1].replaceAll("[^0-9]", ""));
                String valueLine = sc.nextLine().trim();
                String value = valueLine.split(":")[1].replaceAll("[^0-9a-zA-Z]", "");
                BigInteger y = new BigInteger(value, base);
                map.put(x, y);
            }
        }
        sc.close();

        return extractKPoints(map, k); // Take only 'k' points
    }

    static Map<Integer, BigInteger> extractKPoints(Map<Integer, BigInteger> fullMap, int k) {
        Map<Integer, BigInteger> subMap = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<Integer, BigInteger> entry : fullMap.entrySet()) {
            subMap.put(entry.getKey(), entry.getValue());
            count++;
            if (count == k) break;
        }
        return subMap;
    }

    static BigInteger lagrangeInterpolation(Map<Integer, BigInteger> points) {
        BigInteger result = BigInteger.ZERO;
        List<Integer> xList = new ArrayList<>(points.keySet());
        List<BigInteger> yList = new ArrayList<>(points.values());

        for (int i = 0; i < xList.size(); i++) {
            BigInteger xi = BigInteger.valueOf(xList.get(i));
            BigInteger yi = yList.get(i);
            BigInteger term = yi;

            for (int j = 0; j < xList.size(); j++) {
                if (i == j) continue;
                BigInteger xj = BigInteger.valueOf(xList.get(j));
                BigInteger numerator = xj.negate(); // -xj
                BigInteger denominator = xi.subtract(xj);
                term = term.multiply(numerator).divide(denominator);
            }

            result = result.add(term);
        }

        return result;
    }
}