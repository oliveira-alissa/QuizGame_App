
package com.example.gameproject1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionAnswer {
    public static List<Question> questions = new ArrayList<>();

    static {
        // Question 1
        Question question1 = new Question(
                "What does AI stand for?",
                Arrays.asList("Automated Intelligence", "Artificial Intelligence", "Advanced Innovation", "Algorithmic Integration"),
                Arrays.asList("Artificial Intelligence")
        );
        questions.add(question1);

        // Question 2
        Question question2 = new Question(
                "Which programming languages are commonly used in AI development? (Select all that apply)",
                Arrays.asList("Java", "Python", "C++", "HTML"),
                Arrays.asList("Python", "C++")
        );
        questions.add(question2);

        // Question 3
        Question question3 = new Question(
                "Which of the following are examples of narrow AI? (Select all that apply)",
                Arrays.asList("Siri", "Watson", "Deep Blue", "Google Assistant"),
                Arrays.asList("Siri", "Deep Blue", "Google Assistant")
        );
        questions.add(question3);

        // Question 4
        Question question4 = new Question(
                "What is the purpose of natural language processing (NLP) in AI?",
                Arrays.asList(
                        "To understand and generate human language",
                        "To analyze weather patterns",
                        "To simulate natural disasters",
                        "To create virtual reality environments"
                ),
                Arrays.asList("To understand and generate human language")
        );
        questions.add(question4);

        // Question 5
        Question question5 = new Question(
                "What does the acronym IoT stand for in the context of AI?",
                Arrays.asList("Internet of Technology", "Intelligent of Things", "Internet of Things", "Integrated of Technology"),
                Arrays.asList("Internet of Things")
        );
        questions.add(question5);

        // Question 6
        Question question6 = new Question(
                "Which company developed the first widely-known AI program, called ELIZA, in the 1960s?",
                Arrays.asList("Microsoft", "Google", "IBM", "Apple"),
                Arrays.asList("IBM")
        );
        questions.add(question6);

        // Question 7
        Question question7 = new Question(
                "What is the name of the AI assistant developed by Apple?",
                Arrays.asList("Cortana", "Alexa", "Google Assistant", "Siri"),
                Arrays.asList("Siri")
        );
        questions.add(question7);

        // Question 8
        Question question8 = new Question(
                "Which AI technique involves training a model to recognize patterns in data without being explicitly programmed?",
                Arrays.asList("Machine Learning", "Expert Systems", "Neural Networks", "Natural Language Processing"),
                Arrays.asList("Natural Language Processing")
        );
        questions.add(question8);
    }

    public static class Question {
        private String question;
        private List<String> choices;
        private List<String> correctAnswers;

        public Question(String question, List<String> choices, List<String> correctAnswers) {
            this.question = question;
            this.choices = choices;
            this.correctAnswers = correctAnswers;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getChoices() {
            return choices;
        }

        public List<String> getCorrectAnswers() {
            return correctAnswers;
        }
    }
}
