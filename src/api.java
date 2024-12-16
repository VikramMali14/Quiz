import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


public class api {
    public static void main(String[] args) throws IOException, InterruptedException {
        var apiUrl = "https://opentdb.com/api.php?amount=10&category=18&difficulty=medium1&type=multiple";
        var req = HttpRequest.newBuilder().GET().uri(URI.create(apiUrl)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(req, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONArray questions = jsonResponse.getJSONArray("results");

        Scanner scanner = new Scanner(System.in);
        int score = 0;

        for (int i = 0; i < questions.length(); i++) {
            JSONObject questionObj = questions.getJSONObject(i);
            String question = questionObj.getString("question");
            String correctAnswer = questionObj.getString("correct_answer");
            JSONArray incorrectAnswers = questionObj.getJSONArray("incorrect_answers");

            String[] options = new String[4];
            for (int j = 0; j < incorrectAnswers.length(); j++) {
                options[j] = incorrectAnswers.getString(j);
            }
            options[3] = correctAnswer;
            shuffleArray(options);

            System.out.println("\nQuestion " + (i + 1) + ": " + question);
            for (int j = 0; j < options.length; j++) {
                System.out.println((j + 1) + ". " + options[j]);
            }

            System.out.print("Your answer (1-4): ");
            int userAnswer = scanner.nextInt();

            if (options[userAnswer - 1].equals(correctAnswer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + correctAnswer);
            }
        }

        System.out.println("\nYou scored " + score + " out of " + questions.length());
        scanner.close();
    }

     private static void shuffleArray(String[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int index = (int) (Math.random() * (i + 1));
            String temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
    }