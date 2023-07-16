import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign; 
class ResumeBuilderGUI {

    private JFrame frame;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextArea addressArea;
    private JTextArea summaryArea;
    private JTextArea experienceArea;
    private JTextArea educationArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ResumeBuilderGUI window = new ResumeBuilderGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ResumeBuilderGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Resume Builder");
        frame.setBounds(100, 100, 450, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(20, 20, 80, 14);
        frame.getContentPane().add(lblName);

        nameField = new JTextField();
        nameField.setBounds(110, 17, 300, 20);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(20, 50, 80, 14);
        frame.getContentPane().add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(110, 47, 300, 20);
        frame.getContentPane().add(emailField);
        emailField.setColumns(10);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(20, 80, 80, 14);
        frame.getContentPane().add(lblPhone);

        phoneField = new JTextField();
        phoneField.setBounds(110, 77, 300, 20);
        frame.getContentPane().add(phoneField);
        phoneField.setColumns(10);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(20, 110, 80, 14);
        frame.getContentPane().add(lblAddress);

        JScrollPane addressScrollPane = new JScrollPane();
        addressScrollPane.setBounds(110, 107, 300, 80);
        frame.getContentPane().add(addressScrollPane);

        addressArea = new JTextArea();
        addressArea.setLineWrap(true);
        addressScrollPane.setViewportView(addressArea);

        JLabel lblSummary = new JLabel("Summary:");
        lblSummary.setBounds(20, 200, 80, 14);
        frame.getContentPane().add(lblSummary);

        JScrollPane summaryScrollPane = new JScrollPane();
        summaryScrollPane.setBounds(110, 197, 300, 80);
        frame.getContentPane().add(summaryScrollPane);

        summaryArea = new JTextArea();
        summaryArea.setLineWrap(true);
        summaryScrollPane.setViewportView(summaryArea);

        JLabel lblExperience = new JLabel("Experience:");
        lblExperience.setBounds(20, 290, 80, 14);
        frame.getContentPane().add(lblExperience);

        JScrollPane experienceScrollPane = new JScrollPane();
        experienceScrollPane.setBounds(110, 287, 300, 80);
        frame.getContentPane().add(experienceScrollPane);

        experienceArea = new JTextArea();
        experienceArea.setLineWrap(true);
        experienceScrollPane.setViewportView(experienceArea);

        JLabel lblEducation = new JLabel("Education:");
        lblEducation.setBounds(20, 380, 80, 14);
        frame.getContentPane().add(lblEducation);

        JScrollPane educationScrollPane = new JScrollPane();
        educationScrollPane.setBounds(110, 377, 300, 80);
        frame.getContentPane().add(educationScrollPane);

        educationArea = new JTextArea();
        educationArea.setLineWrap(true);
        educationScrollPane.setViewportView(educationArea);

        JButton btnGenerate = new JButton("Generate Resume");
        btnGenerate.setBounds(150, 480, 150, 25);
        frame.getContentPane().add(btnGenerate);

        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressArea.getText();
                String summary = summaryArea.getText();
                String experience = experienceArea.getText();
                String education = educationArea.getText();

                generateResume(name, email, phone, address, summary, experience, education);
            }
        });
    }

    private void generateResume(String name, String email, String phone, String address,
                                String summary, String experience, String education) {
        // Generate PDF resume
        Document pdfDocument = new Document();
        try {
            PdfWriter.getInstance(pdfDocument, new FileOutputStream("resume.pdf"));
            pdfDocument.open();
            pdfDocument.add(new Paragraph("Resume"));

            pdfDocument.add(new Paragraph("Name: " + name));
            pdfDocument.add(new Paragraph("Email: " + email));
            pdfDocument.add(new Paragraph("Phone: " + phone));
            pdfDocument.add(new Paragraph("Address: " + address));
            pdfDocument.add(new Paragraph("Summary: " + summary));
            pdfDocument.add(new Paragraph("Experience: " + experience));
            pdfDocument.add(new Paragraph("Education: " + education));

            pdfDocument.close();
            JOptionPane.showMessageDialog(frame, "PDF resume generated successfully!",
                    "Resume Builder", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error generating PDF resume!",
                    "Resume Builder", JOptionPane.ERROR_MESSAGE);
        }

        // Generate DOC resume
        XWPFDocument docDocument = new XWPFDocument();
        try {
            XWPFParagraph paragraph = docDocument.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setFontSize(18);
            run.setText("Resume");

            addResumeSection(docDocument, "Name", name);
            addResumeSection(docDocument, "Email", email);
            addResumeSection(docDocument, "Phone", phone);
            addResumeSection(docDocument, "Address", address);
            addResumeSection(docDocument, "Summary", summary);
            addResumeSection(docDocument, "Experience", experience);
            addResumeSection(docDocument, "Education", education);

            FileOutputStream docOutputStream = new FileOutputStream("resume.docx");
            docDocument.write(docOutputStream);
            docDocument.close();

            JOptionPane.showMessageDialog(frame, "DOC resume generated successfully!",
                    "Resume Builder", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error generating DOC resume!",
                    "Resume Builder", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addResumeSection(XWPFDocument docDocument, String title, String content) {
        XWPFParagraph paragraph = docDocument.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun titleRun = paragraph.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(12);
        titleRun.setText(title + ":");
        titleRun.addCarriageReturn();

        XWPFRun contentRun = paragraph.createRun();
        contentRun.setFontSize(11);
        contentRun.setText(content);
        contentRun.addCarriageReturn();
    }
}