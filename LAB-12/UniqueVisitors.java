

public class UniqueVisitors{
    public static void main(String args[]){
        String str = "The quick brown fox jumps over the lazy dog.";
        String[] words = str.split("\\W+");
        java.util.Set<String> uniqueWords = new java.util.HashSet<>();

        for (String word : words) {
            word = word.toLowerCase();
            uniqueWords.add(word);
        }

        System.out.println("Unique Visitors:");
        for (String word : uniqueWords) {
            System.out.println(word);
        }  
    }
}