package src;

import java.util.Scanner;

public class PTTest {
	
	private static final int[] calfCutOff = {20, 30};
	private static final int[] coreCutOff = {20, 30};

    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);

        print("How much do you weigh?");
        double weight = input.nextDouble();
        print("How tall are you in inches?");
        double height = input.nextDouble();
        print("How old are you?");
        double age = input.nextDouble();
        
        // calculating BMI
        double bmi = 703*weight/(height*height);
        
        // if lifestyle is sedentary, then more likely to have weaker hammies
        print("On an average day, is your lifestyle sedentary (mostly sitting),"
            + "moderately active (a moderate amount of walking), or very active (meaning a lot of walking)?");
        String lifestyleInput = input.next();
        String lifestyleType = lifestyleInput.charAt(0)=='s' ? "sedentary" : lifestyleInput.charAt(0)=='m' ? "moderately active" : "very active";

        print("For about how long have you been running consistently? (a) less than 6 months (b) 6 months to 1 year (c) over one year");
        char runningTrainingLength = input.next().charAt(0);
        
        print("Do you consistently strengthen your quads?");
        boolean consistentQuadStrength = input.next().toLowerCase().charAt(0)=='n' ? false : true;

        print("Do you consistently strengthen your hamstrings?");
        boolean consistentHamStrength = input.next().toLowerCase().charAt(0)=='n' ? false : true;

        print("Do you consistently strengthen your glutes?");
        boolean consistentGluteStrength = input.next().toLowerCase().charAt(0)=='n' ? false : true;

        // if low number, more likely to have weaker hammies and glutes
        print("Do you do hill workouts (a) once or more per week (b) once a month (c) rarely or (d) never?");
        String hillWktFreq = input.next();
        
        // if yes, then more likely to have weaker hammies and glutes
        print("Do you regularly run on a treadmill?");
        boolean regularlyRunsTreadmill = input.next().toLowerCase().charAt(0)=='n' ? false : true;

        print("How many single leg calf raises can you do on your right leg?");
        int calfRaises = input.nextInt();

        print("How many situps can you do before feeling sore?");
        int situps = input.nextInt();

        // ANALYSIS
        // Quad-dominance
        boolean isQuadDom = isQuadDominant(regularlyRunsTreadmill,hillWktFreq,consistentHamStrength,consistentQuadStrength, consistentGluteStrength, lifestyleType);
        if(isQuadDom)print("You are quad-dominant; recommend more hamstring and glute strengthening than quads");
        else print("you are not quad-dominant; recommend same amount of hamstring and glute strengthening as quads");

        // calf strength
        int calfStr = calvesAreWeak(calfRaises);
        if(calfStr==1) print("Recommend to strengthen calves A LOT");
        else if (calfStr==2) print("recommend to strengthen calves a little bit");
        else print("probably not worth strengthening calves, but instead maintaining strength");
        
        // age
        if(age<15) print("person is young and can recover fast");
        else if (age<30) print("person is relatively young and will take a moderate amount of time to recover");
        else if (age<50) print("person is middle aged ish and will take a somewhat longer time to recover");
        else print("person is old and needs a longer plan");
        
        // shins
        int shinsScore = shins(runningTrainingLength);
        if(shinsScore==1) print("person is likely to have shin injuries; recommend lower leg excercises to help");
        else if(shinsScore==2) print("person is somewhat likely to have shin injuries; recommend some lower leg");
        else print("person probably does not have shin injuries");
        
        // core
        int core = calvesAreWeak(situps);
        if(core==1) print("Recommend to strengthen core A LOT");
        else if (core==2) print("improve core strength a little bit");
        else print("just need to maintain core strength; core exercises are not a priority");
        
        input.close();
    }
    
    public static int shins(char runningLength) {
    	if(runningLength =='a') return 3;
    	else if (runningLength=='b') return 2;
    	else return 1;
    }

    public static int calvesAreWeak(int calfRaises) {
        if(calfRaises<calfCutOff[0]) return 1;
        else if(calfRaises<calfCutOff[1]) return 2;
        else return 3;
    }
    
    public static int coreIsWeak(int situps) {
        if(situps<coreCutOff[0]) return 1;
        else if(situps<coreCutOff[1]) return 2;
        else return 3;
    }
    
    public static String obesityStatus(double bmi) {
    	if(bmi>25) return "obese";
    	else if(bmi> 18.5) return "normal-weight";
    	else return "underweight";
    }

    public static boolean isQuadDominant(boolean regularlyRunsTreadmill, String hillWktFreq, boolean consistentHamStr,
        boolean consistentQuadStr, boolean consistentGluteStr, String lifestyleType) {
            int points = 0;

            if(regularlyRunsTreadmill) points++;

            if(lifestyleType.equals("sedentary")) points++;
            else if(lifestyleType.equals("very active")) points--;
            
            if (hillWktFreq.equals("b")) {
                points++;
            } else if (hillWktFreq.equals("c")) {
                points+=2;
            } else if (hillWktFreq.equals("d")) {
                points+=3;
            }

            if(consistentQuadStr) {
                if(!consistentHamStr) {
                    points++;
                }
                if(!consistentGluteStr) {
                    points++;
                }
            }
            else {
                // prioritizing ham and glute str because quad-dominance is about imbalance,
                // meaning relative imbalance in quads and hams rather than actual weakness 
                // in quads and ham
                if(consistentHamStr) points--;
                if(consistentGluteStr) points--;
            }

            return points<0;
    }

    public static void print(Object o) {
        System.out.println(o);
    }
}