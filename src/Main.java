import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JTextField sizeField; //Box to type how many names
    private JTextArea mergeSortOutput; //Shows Merge Sort names
    private JTextArea quickSortOutput; //Shows Quick Sort names
    private JLabel mergeTimeLabel; //Shows time for Merge Sort
    private JLabel quickTimeLabel; //Shows time for Quick Sort
    private NameGenerator generator; //Makes random names

    //This sets up the window
    public Main() {
        //Give the window a title
        setTitle("Sorting Tester");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        //Initialize the name generator
        generator = new NameGenerator();

        //Make a box at the top for typing
        JPanel inputPanel = new JPanel();
        JLabel numberText = new JLabel("Number of Names:");
        inputPanel.add(numberText);
        sizeField = new JTextField("10000", 10);
        inputPanel.add(sizeField);
        add(inputPanel, BorderLayout.NORTH);

        //Add a button to start sorting
        JButton runButton = new JButton("Run Sorting");
        inputPanel.add(runButton);

        //Make a box to hold both sorting results
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(1, 2)); //Two parts side by side

        //Make a box for Merge Sort
        JPanel mergePanel = new JPanel();
        mergePanel.setLayout(new BorderLayout());
        JLabel mergeTitle = new JLabel("Merge Sort Results");
        mergePanel.add(mergeTitle, BorderLayout.NORTH);

        //Add an area to show sorted names
        mergeSortOutput = new JTextArea(10, 20);
        mergeSortOutput.setEditable(false);
        mergePanel.add(new JScrollPane(mergeSortOutput), BorderLayout.CENTER);
        //Merge sort time
        mergeTimeLabel = new JLabel("Time: 0 ms");
        mergePanel.add(mergeTimeLabel, BorderLayout.SOUTH);
        outputPanel.add(mergePanel);

        //Make a box for Quick Sort
        JPanel quickPanel = new JPanel();
        quickPanel.setLayout(new BorderLayout());
        JLabel quickTitle = new JLabel("Quick Sort Results");
        quickPanel.add(quickTitle, BorderLayout.NORTH);

        //Add an area to show sorted names
        quickSortOutput = new JTextArea(10, 20);
        quickSortOutput.setEditable(false); // You can't type here
        quickPanel.add(new JScrollPane(quickSortOutput), BorderLayout.CENTER);

        //Quick sort time
        quickTimeLabel = new JLabel("Time: 0 ms");
        quickPanel.add(quickTimeLabel, BorderLayout.SOUTH);
        outputPanel.add(quickPanel);


        add(outputPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();

        //Add a button with info about sorting
        JButton complexityButton = new JButton("Show Sorting Info");
        bottomPanel.add(complexityButton);
        add(bottomPanel, BorderLayout.SOUTH);

        //Sets  action of the sort button
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runSorting();
            }
        });

        //Sets action of the info button
        complexityButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String info = "Merge Sort:\n" +
                        "- Time Complexity: O(n log n) average and worst case\n" +
                        "- Space Complexity: O(n) due to additional arrays\n\n" +
                        "Quick Sort:\n" +
                        "- Time Complexity: O(n log n) average, O(n²) worst case\n" +
                        "- Space Complexity: O(log n) average for stack frames";
                JOptionPane.showMessageDialog(Main.this, info,
                        "Sorting Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void runSorting() {
        try {
            //Takes the number of names we typed
            int size = Integer.parseInt(sizeField.getText());
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number.");
                return;
            }

            //Generate names
            String[] mergeNames = generator.generateNames(size);
            String[] quickNames = mergeNames.clone();

            //Merge Sort
            double startTime = System.nanoTime();
            MergeSort.mergeSort(mergeNames);
            double endTime = System.nanoTime();
            double mergeTime = (endTime - startTime) / 1_000_000;

            //Display first 100 names
            String mergeText = "";
            for (int i = 0; i < Math.min(100, mergeNames.length); i++) {
                if( i  < Math.min(99, mergeNames.length - 1)) {
                    mergeText += mergeNames[i] + ", ";
                }
            }
            mergeSortOutput.setText(mergeText);
            mergeTimeLabel.setText("Time: " + String.format("%.2f", mergeTime) + " ms");

            //Quick Sort
            startTime = System.nanoTime();
            QuickSort.quickSort(quickNames, 0, quickNames.length - 1);
            endTime = System.nanoTime();
            double quickTime = (endTime - startTime) / 1_000_000;

            //Display first 100 names
            String quickText = "";
            for (int i = 0; i < Math.min(100, quickNames.length); i++) {
                if( i  < Math.min(99, quickNames.length - 1)) {
                    quickText += quickNames[i] + ", ";
                }
            }
            quickSortOutput.setText(quickText);
            quickTimeLabel.setText("Time: " + String.format("%.2f", quickTime) + " ms");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        //Thread safe GUI initialization
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}