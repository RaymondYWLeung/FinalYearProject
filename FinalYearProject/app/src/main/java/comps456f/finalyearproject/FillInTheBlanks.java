package comps456f.finalyearproject;

public class FillInTheBlanks {
    private String questionNumber;
    private String questionType;
    private String questionTitle;
    //private String content[];
    private String answer;

    FillInTheBlanks(String questionNumber, String questionType, String questionTitle, String answer){
        this.questionNumber = questionNumber;
        this.questionType = questionType;
        this.questionTitle = questionTitle;
       // this.content = content;
        this.answer = answer;
    }

    /*public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }*/

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
