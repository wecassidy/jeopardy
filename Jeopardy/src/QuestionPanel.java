
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuestionPanel extends JPanel implements ActionListener{
  JButton[] answers= new JButton[6];
  JLabel question;
  Question qObj;
  GridBagConstraints gbc = new GridBagConstraints();
  private Jeopardy game;
  private int guesses;

  Player[] players;

  public QuestionPanel(Question question, Jeopardy game){
    this.game = game;
    this.guesses = 0;

    qObj = question;
    this.setPreferredSize(new Dimension(600, 500));
    this.setLayout(new GridBagLayout());

    this.setSize(new Dimension(600, 430));

    for (int i = 0; i < 4; i++){
      answers[i] = new JButton(question.getAnswer()[i]);
      answers[i].addActionListener(this);
    }

    this.question = new JLabel(question.getQuestion());
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.insets = new Insets(10, 10, 120 ,10);
    add(this.question, gbc);

    for(int i = 0; i < 4; i++){
      //first column of questions is in column 1
      //second column of questions is in column 4
      gbc.gridx = 1 + (i % 2) * 4;
      //first row is in row 5
      //second row is in row 7
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.ipadx = 20;
      gbc.ipady = 30;
      gbc.insets = new Insets(0, 10, 10 ,10);

      add(answers[i], gbc);
    }
  }

  public void actionPerformed (ActionEvent e){

    int index = 0;

    for (int i = 0; i < 6; i++){
      if (answers[i] == (JButton)(e.getSource())){
        index = i;
      }
    }

    Player current = game.players[game.getTurn()];
    guesses++;

    if(qObj.checkGuess(index)){
      //increment $$ here
      //display amount earned and balance
      CardLayout cl = (CardLayout)(game.questionArea.getLayout());
      cl.first(game.questionArea);

      current.addDollars(qObj.getValue());
      game.updateDollars();
      JOptionPane.showMessageDialog(game, current.getName() + " now has $" + current.getDollars(), "Correct", JOptionPane.INFORMATION_MESSAGE);
      // TODO custom check icon
    }
    else {
      //blackout the answer chosen if answer is wrong
      answers[index].setEnabled(false);
      game.incrementTurn();
       if (guesses == 3) {
        JOptionPane.showMessageDialog(game, "Incorrect. The correct answer was " + qObj.getAnswer()[qObj.getCorrect()]);
        CardLayout cl = (CardLayout)(game.questionArea.getLayout());
        cl.first(game.questionArea);
       } else {
         current = game.players[game.getTurn()];
         JOptionPane.showMessageDialog(game, "Incorrect. It is now " + current.getName() + "'s turn.", "Incorrect", JOptionPane.INFORMATION_MESSAGE);
       }
    }

  }

}
