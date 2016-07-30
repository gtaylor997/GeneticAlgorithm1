import javax.swing.JFrame;

public class GeneticAlgorithm1 {

	public GeneticAlgorithm1(String title) {

	}

	public static void main(String[] args) {
		Simulator sim = new Simulator();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(sim);
		frame.setVisible(true);
		sim.start();
	}

}
