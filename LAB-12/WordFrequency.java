public class WordFrequency{
    public static void main(String args[]){
        String str = "The quick brown fox jumps over the lazy dog.";
        String[] words = str.split("\\W+");
        java.util.Map<String, Integer> wordCount = new java.util.HashMap<>();

        for (String word : words) {
            word = word.toLowerCase();
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        System.out.println("Word Frequency:");
        for (java.util.Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}