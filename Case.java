import java.awt.*;
import java.util.*;

class Case{
		private Color color;
		private int number;
		private Image image;
		
		public Case(Color color, int number){
			this.color = color;
			this.number = number;
		}
		public Case(Image image, int number){
			this.image = image;
			this.number = number;
		}
		public Color getColor(){
			return color;
		}
		public int getNumber(){
			return number;
		}
		public Image getImage(){
			return image;
		}
}
	
class QuestionAboutCase extends Case{
	private String[] attempts;
	private boolean correctAnswer = false;
	
	public QuestionAboutCase(Image image, int number, String[] attempts){
		super(image, number);
		
		for(int i = 0; i < attempts.length; i++){
			Integer attempt = Integer.parseInt(attempts[i]);
			if(attempt == 0){
				attempt = ((int)(Math.random() * 37)) + 1;
				attempts[i] = attempt.toString();
			}
		}
		this.attempts = attempts;
	}
	
	public String[] getAttempts(){
		return attempts;
	}
	
	public boolean getCorrectAnswer(){
		return correctAnswer;
	}
	
	public void setCorrectAnswer(boolean correctAnswer){
		this.correctAnswer = correctAnswer;
	}
	
	//метод, чтобы перемешивать варианты ответов
	public void shuffleAttempts(){
		java.util.List<String> stringAttemptsList = Arrays.asList(attempts);
		Collections.shuffle(stringAttemptsList);
		stringAttemptsList.toArray(attempts);
	}
}