package comps456f.finalyearproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MultipleChoice {
    private String questionNumber;
    private String questionType;
    private String questionTitle;
    private ArrayList<String> choices = new ArrayList<String>();
    private String answer;

    MultipleChoice(String questionNumber, String questionType, String questionTitle, ArrayList<String> choices, String answer){
        this.questionNumber = questionNumber;
        this.questionType = questionType;
        this.questionTitle = questionTitle;
        this.choices = choices;
        Collections.shuffle(choices);
        this.answer = answer;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getAnswer() {

        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionTitle() {

        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionNumber() {

        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionType() {

        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}
