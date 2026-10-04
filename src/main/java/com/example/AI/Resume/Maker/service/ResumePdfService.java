package com.example.AI.Resume.Maker.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.GeneratedResumeContent;
import com.example.AI.Resume.Maker.dto.ResumeEducation;
import com.example.AI.Resume.Maker.dto.ResumeExperience;
import com.example.AI.Resume.Maker.dto.ResumeProject;

@Service
public class ResumePdfService {

    private static final float PAGE_WIDTH =
            PDRectangle.A4.getWidth();

    private static final float PAGE_HEIGHT =
            PDRectangle.A4.getHeight();

    private static final float MARGIN = 50;

    private static final float CONTENT_WIDTH =
            PAGE_WIDTH - (MARGIN * 2);

    private static final float NORMAL_FONT_SIZE = 9.5f;

    private static final float SMALL_FONT_SIZE = 8.5f;

    private static final float SECTION_FONT_SIZE = 11;

    private static final float NAME_FONT_SIZE = 18;

    private static final float ROLE_FONT_SIZE = 10.5f;

    private static final float LINE_HEIGHT = 13;

    private final PDType1Font regularFont =
            new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA
            );

    private final PDType1Font boldFont =
            new PDType1Font(
                    Standard14Fonts.FontName.HELVETICA_BOLD
            );

    public byte[] generatePdf(
            GeneratedResumeContent resume)
            throws IOException {

        if (resume == null) {
            throw new IllegalArgumentException(
                    "Resume data cannot be null."
            );
        }

        try (
                PDDocument document =
                        new PDDocument();

                ByteArrayOutputStream outputStream =
                        new ByteArrayOutputStream()
        ) {

            PdfWriter writer =
                    new PdfWriter(document);

            writer.addNewPage();

            writeHeader(
                    writer,
                    resume
            );

            writeSection(
                    writer,
                    "PROFESSIONAL SUMMARY"
            );

            writeParagraph(
                    writer,
                    safe(resume.getProfessionalSummary()),
                    NORMAL_FONT_SIZE
            );

            writeSection(
                    writer,
                    "TECHNICAL SKILLS"
            );

            writeSkills(
                    writer,
                    resume.getSkills()
            );

            if (!safeList(
                    resume.getExperience()
            ).isEmpty()) {

                writeSection(
                        writer,
                        "EXPERIENCE"
                );

                writeExperience(
                        writer,
                        resume.getExperience()
                );
            }

            if (!safeList(
                    resume.getProjects()
            ).isEmpty()) {

                writeSection(
                        writer,
                        "PROJECTS"
                );

                writeProjects(
                        writer,
                        resume.getProjects()
                );
            }

            if (!safeList(
                    resume.getEducation()
            ).isEmpty()) {

                writeSection(
                        writer,
                        "EDUCATION"
                );

                writeEducation(
                        writer,
                        resume.getEducation()
                );
            }

            if (!safeList(
                    resume.getCertifications()
            ).isEmpty()) {

                writeSection(
                        writer,
                        "CERTIFICATIONS"
                );

                writeBulletList(
                        writer,
                        resume.getCertifications(),
                        NORMAL_FONT_SIZE
                );
            }

            if (!safeList(
                    resume.getAchievements()
            ).isEmpty()) {

                writeSection(
                        writer,
                        "ACHIEVEMENTS"
                );

                writeBulletList(
                        writer,
                        resume.getAchievements(),
                        NORMAL_FONT_SIZE
                );
            }

            if (!safeList(
                    resume.getLanguages()
            ).isEmpty()) {

                writeSection(
                        writer,
                        "LANGUAGES"
                );

                writeParagraph(
                        writer,
                        String.join(
                                " | ",
                                safeList(
                                        resume.getLanguages()
                                )
                        ),
                        NORMAL_FONT_SIZE
                );
            }

            writer.closeCurrentStream();

            document.save(
                    outputStream
            );

            return outputStream.toByteArray();
        }
    }

    private void writeHeader(
            PdfWriter writer,
            GeneratedResumeContent resume)
            throws IOException {

        String fullName =
                safe(resume.getFullName());

        if (!fullName.isBlank()) {

            writer.writeCentered(
                    fullName,
                    boldFont,
                    NAME_FONT_SIZE,
                    24
            );
        }

        String contactLine =
                buildContactLine(resume);

        if (!contactLine.isBlank()) {

            writer.writeCentered(
                    contactLine,
                    regularFont,
                    SMALL_FONT_SIZE,
                    14
            );
        }

        writer.addVerticalSpace(4);

        String professionalRole =
                buildProfessionalRole(
                        resume
                );

        if (!professionalRole.isBlank()) {

            writer.writeCentered(
                    professionalRole,
                    boldFont,
                    ROLE_FONT_SIZE,
                    15
            );
        }

        writer.drawHorizontalLine();

        writer.addVerticalSpace(7);
    }

    private String buildContactLine(
            GeneratedResumeContent resume) {

        StringBuilder result =
                new StringBuilder();

        addContactValue(
                result,
                resume.getEmail()
        );

        addContactValue(
                result,
                resume.getPhone()
        );

        addContactValue(
                result,
                resume.getLocation()
        );

        addContactValue(
                result,
                resume.getLinkedin()
        );

        addContactValue(
                result,
                resume.getGithub()
        );

        addContactValue(
                result,
                resume.getPortfolio()
        );

        return result.toString();
    }

    private void addContactValue(
            StringBuilder result,
            String value) {

        String cleaned =
                safe(value);

        if (cleaned.isBlank()) {
            return;
        }

        if (!result.isEmpty()) {
            result.append("  |  ");
        }

        result.append(cleaned);
    }

    private String buildProfessionalRole(
            GeneratedResumeContent resume) {

        /*
         * The generated resume does not store the target role
         * inside GeneratedResumeContent.
         *
         * Therefore the role line is intentionally omitted here.
         * The controller can add the target role later if we decide
         * to include it in the content DTO.
         */
        return "";
    }

    private void writeSection(
            PdfWriter writer,
            String title)
            throws IOException {

        writer.ensureSpace(30);

        writer.writeText(
                title,
                boldFont,
                SECTION_FONT_SIZE
        );

        writer.drawHorizontalLine();

        writer.addVerticalSpace(4);
    }

    private void writeSkills(
            PdfWriter writer,
            List<String> skills)
            throws IOException {

        List<String> safeSkills =
                safeList(skills);

        if (safeSkills.isEmpty()) {
            return;
        }

        String skillText =
                String.join(
                        " | ",
                        safeSkills
                );

        writeParagraph(
                writer,
                skillText,
                NORMAL_FONT_SIZE
        );
    }

    private void writeExperience(
            PdfWriter writer,
            List<ResumeExperience> experiences)
            throws IOException {

        for (ResumeExperience experience :
                safeList(experiences)) {

            writer.ensureSpace(65);

            String jobTitle =
                    safe(experience.getJobTitle());

            String company =
                    safe(experience.getCompany());

            String firstLine =
                    buildExperienceHeading(
                            jobTitle,
                            company
                    );

            if (!firstLine.isBlank()) {

                writer.writeText(
                        firstLine,
                        boldFont,
                        NORMAL_FONT_SIZE
                );

                writer.addVerticalSpace(2);
            }

            String details =
                    buildExperienceDetails(
                            experience
                    );

            if (!details.isBlank()) {

                writer.writeText(
                        details,
                        regularFont,
                        SMALL_FONT_SIZE
                );

                writer.addVerticalSpace(3);
            }

            writeBulletList(
                    writer,
                    experience.getResponsibilities(),
                    NORMAL_FONT_SIZE
            );

            writer.addVerticalSpace(5);
        }
    }

    private String buildExperienceHeading(
            String jobTitle,
            String company) {

        if (!jobTitle.isBlank()
                && !company.isBlank()) {

            return jobTitle
                    + " - "
                    + company;
        }

        if (!jobTitle.isBlank()) {
            return jobTitle;
        }

        return company;
    }

    private String buildExperienceDetails(
            ResumeExperience experience) {

        StringBuilder result =
                new StringBuilder();

        String location =
                safe(experience.getLocation());

        String startDate =
                safe(experience.getStartDate());

        String endDate =
                safe(experience.getEndDate());

        if (!location.isBlank()) {
            result.append(location);
        }

        String dateRange =
                buildDateRange(
                        startDate,
                        endDate
                );

        if (!dateRange.isBlank()) {

            if (!result.isEmpty()) {
                result.append("  |  ");
            }

            result.append(dateRange);
        }

        return result.toString();
    }

    private void writeProjects(
            PdfWriter writer,
            List<ResumeProject> projects)
            throws IOException {

        for (ResumeProject project :
                safeList(projects)) {

            writer.ensureSpace(60);

            String name =
                    safe(project.getName());

            if (!name.isBlank()) {

                writer.writeText(
                        name,
                        boldFont,
                        NORMAL_FONT_SIZE
                );

                writer.addVerticalSpace(2);
            }

            String description =
                    safe(project.getDescription());

            if (!description.isBlank()) {

                writeParagraph(
                        writer,
                        description,
                        NORMAL_FONT_SIZE
                );
            }

            List<String> technologies =
                    safeList(
                            project.getTechnologies()
                    );

            if (!technologies.isEmpty()) {

                String technologyText =
                        "Technologies: "
                        + String.join(
                                ", ",
                                technologies
                        );

                writer.writeWrappedText(
                        technologyText,
                        regularFont,
                        SMALL_FONT_SIZE
                );

                writer.addVerticalSpace(2);
            }

            writeBulletList(
                    writer,
                    project.getContributions(),
                    NORMAL_FONT_SIZE
            );

            String projectUrl =
                    safe(project.getProjectUrl());

            if (!projectUrl.isBlank()) {

                writer.writeWrappedText(
                        projectUrl,
                        regularFont,
                        SMALL_FONT_SIZE
                );
            }

            writer.addVerticalSpace(5);
        }
    }

    private void writeEducation(
            PdfWriter writer,
            List<ResumeEducation> education)
            throws IOException {

        for (ResumeEducation item :
                safeList(education)) {

            writer.ensureSpace(50);

            String degree =
                    safe(item.getDegree());

            String institution =
                    safe(item.getInstitution());

            if (!degree.isBlank()
                    && !institution.isBlank()) {

                writer.writeText(
                        degree + " - " + institution,
                        boldFont,
                        NORMAL_FONT_SIZE
                );

            } else if (!degree.isBlank()) {

                writer.writeText(
                        degree,
                        boldFont,
                        NORMAL_FONT_SIZE
                );

            } else {

                writer.writeText(
                        institution,
                        boldFont,
                        NORMAL_FONT_SIZE
                );
            }

            writer.addVerticalSpace(2);

            String details =
                    buildEducationDetails(item);

            if (!details.isBlank()) {

                writer.writeWrappedText(
                        details,
                        regularFont,
                        SMALL_FONT_SIZE
                );
            }

            writer.addVerticalSpace(5);
        }
    }

    private String buildEducationDetails(
            ResumeEducation education) {

        StringBuilder result =
                new StringBuilder();

        String location =
                safe(education.getLocation());

        String dateRange =
                buildDateRange(
                        safe(education.getStartDate()),
                        safe(education.getEndDate())
                );

        String grade =
                safe(education.getGrade());

        if (!location.isBlank()) {
            result.append(location);
        }

        if (!dateRange.isBlank()) {

            if (!result.isEmpty()) {
                result.append("  |  ");
            }

            result.append(dateRange);
        }

        if (!grade.isBlank()) {

            if (!result.isEmpty()) {
                result.append("  |  ");
            }

            result.append(grade);
        }

        return result.toString();
    }

    private String buildDateRange(
            String startDate,
            String endDate) {

        if (!startDate.isBlank()
                && !endDate.isBlank()) {

            return startDate
                    + " - "
                    + endDate;
        }

        if (!startDate.isBlank()) {
            return startDate;
        }

        return endDate;
    }

    private void writeParagraph(
            PdfWriter writer,
            String text,
            float fontSize)
            throws IOException {

        String cleaned =
                safe(text);

        if (cleaned.isBlank()) {
            return;
        }

        writer.writeWrappedText(
                cleaned,
                regularFont,
                fontSize
        );

        writer.addVerticalSpace(5);
    }

    private void writeBulletList(
            PdfWriter writer,
            List<String> items,
            float fontSize)
            throws IOException {

        for (String item :
                safeList(items)) {

            String cleaned =
                    safe(item);

            if (cleaned.isBlank()) {
                continue;
            }

            writer.writeBullet(
                    cleaned,
                    regularFont,
                    fontSize
            );

            writer.addVerticalSpace(2);
        }
    }

    private String safe(String value) {

        if (value == null) {
            return "";
        }

        return sanitizePdfText(
                value.trim()
        );
    }

    private String sanitizePdfText(
            String value) {

        /*
         * PDFBox standard fonts such as Helvetica do not
         * support every Unicode character. Normalize common
         * punctuation so AI-generated text remains printable.
         */
    	return value
    	        .replace('\u2018', '\'')
    	        .replace('\u2019', '\'')
    	        .replace('\u201C', '"')
    	        .replace('\u201D', '"')
    	        .replace('\u2013', '-')
    	        .replace('\u2014', '-')
    	        .replace("\u2026", "...")
    	        .replace('\u00A0', ' ');
    }

    private <T> List<T> safeList(
            List<T> list) {

        if (list == null) {
            return List.of();
        }

        return list;
    }

    private class PdfWriter {

        private final PDDocument document;

        private PDPage currentPage;

        private PDPageContentStream contentStream;

        private float currentY;

        PdfWriter(
                PDDocument document) {

            this.document = document;
        }

        void addNewPage()
                throws IOException {

            closeCurrentStream();

            currentPage =
                    new PDPage(
                            PDRectangle.A4
                    );

            document.addPage(
                    currentPage
            );

            contentStream =
                    new PDPageContentStream(
                            document,
                            currentPage
                    );

            currentY =
                    PAGE_HEIGHT - MARGIN;
        }

        void ensureSpace(
                float requiredHeight)
                throws IOException {

            if (currentY - requiredHeight
                    < MARGIN) {

                addNewPage();
            }
        }

        void writeText(
                String text,
                PDType1Font font,
                float fontSize)
                throws IOException {

            ensureSpace(
                    fontSize + 4
            );

            contentStream.beginText();

            contentStream.setFont(
                    font,
                    fontSize
            );

            contentStream.newLineAtOffset(
                    MARGIN,
                    currentY
            );

            contentStream.showText(
                    sanitizePdfText(text)
            );

            contentStream.endText();

            currentY -=
                    fontSize + 4;
        }

        void writeCentered(
                String text,
                PDType1Font font,
                float fontSize,
                float spacing)
                throws IOException {

            ensureSpace(
                    fontSize + spacing
            );

            String cleaned =
                    sanitizePdfText(text);

            float textWidth =
                    font.getStringWidth(
                            cleaned
                    ) / 1000 * fontSize;

            float x =
                    (PAGE_WIDTH - textWidth) / 2;

            contentStream.beginText();

            contentStream.setFont(
                    font,
                    fontSize
            );

            contentStream.newLineAtOffset(
                    x,
                    currentY
            );

            contentStream.showText(
                    cleaned
            );

            contentStream.endText();

            currentY -= spacing;
        }

        void writeWrappedText(
                String text,
                PDType1Font font,
                float fontSize)
                throws IOException {

            List<String> lines =
                    wrapText(
                            sanitizePdfText(text),
                            font,
                            fontSize,
                            CONTENT_WIDTH
                    );

            for (String line : lines) {

                ensureSpace(
                        fontSize + 3
                );

                contentStream.beginText();

                contentStream.setFont(
                        font,
                        fontSize
                );

                contentStream.newLineAtOffset(
                        MARGIN,
                        currentY
                );

                contentStream.showText(
                        line
                );

                contentStream.endText();

                currentY -=
                        LINE_HEIGHT;
            }
        }

        void writeBullet(
                String text,
                PDType1Font font,
                float fontSize)
                throws IOException {

            String prefix =
                    "- ";

            float prefixWidth =
                    font.getStringWidth(
                            prefix
                    ) / 1000 * fontSize;

            List<String> lines =
                    wrapText(
                            sanitizePdfText(text),
                            font,
                            fontSize,
                            CONTENT_WIDTH
                                    - prefixWidth
                    );

            for (int i = 0;
                    i < lines.size();
                    i++) {

                ensureSpace(
                        fontSize + 3
                );

                String line;

                if (i == 0) {

                    line =
                            prefix
                            + lines.get(i);

                } else {

                    line =
                            "  "
                            + lines.get(i);
                }

                contentStream.beginText();

                contentStream.setFont(
                        font,
                        fontSize
                );

                contentStream.newLineAtOffset(
                        MARGIN,
                        currentY
                );

                contentStream.showText(
                        line
                );

                contentStream.endText();

                currentY -=
                        LINE_HEIGHT;
            }
        }

        void drawHorizontalLine()
                throws IOException {

            ensureSpace(8);

            contentStream.moveTo(
                    MARGIN,
                    currentY
            );

            contentStream.lineTo(
                    PAGE_WIDTH - MARGIN,
                    currentY
            );

            contentStream.stroke();

            currentY -= 7;
        }

        void addVerticalSpace(
                float amount) {

            currentY -= amount;
        }

        void closeCurrentStream()
                throws IOException {

            if (contentStream != null) {

                contentStream.close();

                contentStream = null;
            }
        }

        private List<String> wrapText(
                String text,
                PDType1Font font,
                float fontSize,
                float maxWidth)
                throws IOException {

            List<String> lines =
                    new java.util.ArrayList<>();

            if (text == null
                    || text.isBlank()) {

                return lines;
            }

            String[] paragraphs =
                    text.split("\\r?\\n");

            for (String paragraph :
                    paragraphs) {

                if (paragraph.isBlank()) {

                    lines.add("");

                    continue;
                }

                String[] words =
                        paragraph
                                .trim()
                                .split("\\s+");

                StringBuilder currentLine =
                        new StringBuilder();

                for (String word :
                        words) {

                    String candidate;

                    if (currentLine
                            .isEmpty()) {

                        candidate = word;

                    } else {

                        candidate =
                                currentLine
                                        .toString()
                                + " "
                                + word;
                    }

                    float width =
                            font.getStringWidth(
                                    candidate
                            )
                            / 1000
                            * fontSize;

                    if (width <= maxWidth) {

                        currentLine.setLength(0);

                        currentLine.append(
                                candidate
                        );

                    } else {

                        if (!currentLine
                                .isEmpty()) {

                            lines.add(
                                    currentLine
                                            .toString()
                            );
                        }

                        currentLine.setLength(0);

                        currentLine.append(
                                word
                        );
                    }
                }

                if (!currentLine
                        .isEmpty()) {

                    lines.add(
                            currentLine.toString()
                    );
                }
            }

            return lines;
        }
    }
}