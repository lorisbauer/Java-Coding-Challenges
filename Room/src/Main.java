
public class Main {
    static double totLength = 4.05;
    static double wardrobeLength = 0.6;
    static double shelfDepth = 0.7;
    static double bookshelfLength = 0.4;
    static double tableLength = 1.6;

    static double totDepth = 1.2;
    static double unknownDepthInc = 0.04;


    public static void main(String[] args) {
        double availableLength = totLength - wardrobeLength;
        double cornerLength = shelfDepth / Math.tan(4.0 * Math.PI / 9.0);
        double officeLength = cornerLength + 2.0 * bookshelfLength + tableLength;

        double deltaLength = availableLength - officeLength;
        double deltaDepth = totDepth + unknownDepthInc - shelfDepth;
        double passageDist = Math.sqrt(deltaLength * deltaLength + deltaDepth * deltaDepth);

        System.out.printf("Width of passage (m): %f\n", passageDist);
    }
}
