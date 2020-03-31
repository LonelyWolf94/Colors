import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

public class Colors{
	private ArrayList<QuestionAboutCase> questionsAboutCases = new ArrayList<QuestionAboutCase>();
	private MyDrawPanel panel;
	private JTextField textField;
	private JCheckBox[] arrCheckBox = new JCheckBox[4];
	private byte index = 0;
	private int quantityOfUnsuccessfulAttempts = 0;
	
	/*private JTextField textFieldHint;
	private JButton buttonHint;
	private JButton buttonPrev;*/
	
	public static void main(String[] args){
		new Colors().go();
	}
	
	private void go(){
		JFrame frame = new JFrame("Colors");
		frame.setSize(479, 833);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initCases();
		
		//Заглушка для кнопки Hint и поля для вывода
		/*buttonHint = new JButton("Hint");
		frame.getContentPane().add(BorderLayout.NORTH, buttonHint);
		textFieldHint = new JTextField(2);
		frame.getContentPane().add(BorderLayout.NORTH, textFieldHint);*/
		
		//Заглушка для кнопки Prev 
		/*buttonPrev = new JButton("Prev");
		frame.getContentPane().add(BorderLayout.WEST, buttonPrev);*/
		
		panel = new MyDrawPanel();
		frame.getContentPane().add(BorderLayout.CENTER, panel);
		
		textField = new JTextField();
		
		textField.addActionListener(new CheckColorActionListener());
		
		JPanel panelOut = new JPanel();
		JPanel panelIn1 = new JPanel();
		JPanel panelIn2 = new JPanel();
		
		MyItemListener myItemListener = new MyItemListener();
		
		//добавляю: в массив arrCheckBox новые объекты, добавляю их на панель и добавляю
		//слушателя
		for(int i = 0; i < arrCheckBox.length; i++){
			arrCheckBox[i] = new JCheckBox(questionsAboutCases.get(index).getAttempts()[i]);
			panelIn1.add(arrCheckBox[i]);
			arrCheckBox[i].addItemListener(myItemListener);
		}
			
		panelIn2.setLayout(new BorderLayout());
		panelIn2.add(textField);
		
		panelOut.setLayout(new BoxLayout(panelOut, BoxLayout.Y_AXIS));
		
		panelOut.add(panelIn1);
		panelOut.add(panelIn2);
		
		frame.getContentPane().add(BorderLayout.SOUTH, panelOut);
		
		JButton buttonNext = new JButton("Next");
		frame.getContentPane().add(BorderLayout.EAST, buttonNext);
		buttonNext.addActionListener(new MyActionListener());
		
		frame.setVisible(true);
		//adding the cursor back into textField
		textField.requestFocus();
	}
	
	private void initCases(){
		ArrayList<String[]> arrListOfAnswers = new ArrayList<String[]>();
		
		arrListOfAnswers.add(new String[] {"1", "4", "0", "0"});	//1
		arrListOfAnswers.add(new String[] {"2", "3", "0", "0"});	//2
		arrListOfAnswers.add(new String[] {"3", "2", "6", "0"});	//3
		arrListOfAnswers.add(new String[] {"4", "5", "1", "0"});	//4
		arrListOfAnswers.add(new String[] {"5", "6", "1", "0"});	//5
		
		
		for(int i = 0; i < arrListOfAnswers.size(); i++){
                        URL url = Colors.class.getResource("/" + (i + 1) + ".jpg");
			questionsAboutCases.add(i, new QuestionAboutCase(new ImageIcon(url).getImage(), i+1, arrListOfAnswers.get(i)));
			//questionsAboutCases.add(i, new QuestionAboutCase(new ImageIcon("Cases/" + (i + 1) +".jpg").getImage(), i+1, arrListOfAnswers.get(i)));
                        
		}
		
		//перемешиваем варианты ответов
		for(int i = 0; i < questionsAboutCases.size(); i++){
			questionsAboutCases.get(i).shuffleAttempts();
		}
		
		Collections.shuffle(questionsAboutCases);
	}
	
	private class MyDrawPanel extends JPanel{
		public void paintComponent(Graphics g){
			g.drawImage(questionsAboutCases.get(index).getImage(), 0, 0, this);
			//g.setColor(cases.get(index).getColor());
			//g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	
	private class MyItemListener implements ItemListener{
		public void itemStateChanged(ItemEvent event){
			JCheckBox checkBox = (JCheckBox)event.getItem();
			if (checkBox.isSelected()){
				for(int i = 0; i < arrCheckBox.length; i++){
					if(!checkBox.getLabel().equals(arrCheckBox[i].getLabel())){
						arrCheckBox[i].setSelected(false);
					}
				}
				textField.setText(checkBox.getLabel());
				
				int attempt = Integer.parseInt(checkBox.getLabel());
				
				if(attempt == questionsAboutCases.get(index).getNumber()){
					textField.setBackground(Color.GREEN);
					questionsAboutCases.get(index).setCorrectAnswer(true);
				} else{
					textField.setBackground(Color.RED);
					quantityOfUnsuccessfulAttempts++;
				}
			}
		}
	}
	
	private class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			for(int i = 0; i < arrCheckBox.length; i++){
				arrCheckBox[i].setSelected(false);
			}
			
			if(index < questionsAboutCases.size() - 1){
				index++;
				panel.repaint();
				
				//загружаю в checkBox-ы новые данные(варианты ответов)
				for(int i = 0; i < arrCheckBox.length; i++){
					arrCheckBox[i].setText(questionsAboutCases.get(index).getAttempts()[i]);
				}
				
				textField.setText("");
				textField.setBackground(Color.WHITE);
				textField.requestFocus();
			} else {
				//подсчет количества правильных ответов
				int quantityOfCorrectAnswers = 0;
				for(int index = 0; index < questionsAboutCases.size(); index++){
					if(questionsAboutCases.get(index).getCorrectAnswer()){
						quantityOfCorrectAnswers++;
					}
				}
				
				JFrame exitFrame = new JFrame("The end");
				JTextArea exitTextArea = new JTextArea("The end.\n" + "The number of questions is:\t" + questionsAboutCases.size() + "\n\n" +
				"The number of correct answers is:\t" + quantityOfCorrectAnswers + "\n" +
				"The number of unsuccessful attempts is:\t" +quantityOfUnsuccessfulAttempts);
				exitTextArea.setEditable(false);
				exitFrame.add(exitTextArea);
				exitFrame.setSize(310, 200);
				exitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				exitFrame.setVisible(true);
			}
		}
	}
	
	private class CheckColorActionListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			int attempt = Integer.parseInt(event.getActionCommand());
			if(attempt == questionsAboutCases.get(index).getNumber()){
				textField.setBackground(Color.GREEN);
				questionsAboutCases.get(index).setCorrectAnswer(true);
			} else{
				textField.setBackground(Color.RED);
				quantityOfUnsuccessfulAttempts++;
			}
		}
	}
}