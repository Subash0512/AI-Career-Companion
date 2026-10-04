package com.example.AI.Resume.Maker.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.LearningResource;

@Service
public class LearningResourceService {

    private final List<ResourceDefinition> resources =
            createResourceCatalog();

    /**
     * Returns learning resources for the supplied
     * missing skills.
     */
    public List<LearningResource> getResources(
            List<String> requestedSkills) {

        if (requestedSkills == null
                || requestedSkills.isEmpty()) {

            return List.of();
        }

        List<LearningResource> results =
                new ArrayList<>();

        Set<String> processedSkills =
                new LinkedHashSet<>();

        for (String skill : requestedSkills) {

            if (skill == null
                    || skill.isBlank()) {
                continue;
            }

            String cleanSkill =
                    skill.trim();

            String normalizedSkill =
                    normalize(cleanSkill);

            if (normalizedSkill.isBlank()
                    || !processedSkills.add(
                            normalizedSkill)) {
                continue;
            }

            List<ResourceDefinition> matches =
                    resources.stream()
                            .filter(
                                    resource ->
                                            resource.matches(
                                                    normalizedSkill
                                            )
                            )
                            .sorted(
                                    Comparator.comparingInt(
                                            ResourceDefinition::priority
                                    )
                            )
                            .limit(5)
                            .toList();

            /*
             * If no specialized platform matches,
             * return reliable broad-search platforms.
             */
            if (matches.isEmpty()) {

                matches =
                        resources.stream()
                                .filter(
                                        ResourceDefinition::isGeneralResource
                                )
                                .sorted(
                                        Comparator.comparingInt(
                                                ResourceDefinition::priority
                                        )
                                )
                                .limit(3)
                                .toList();
            }

            for (ResourceDefinition resource :
                    matches) {

                results.add(
                        resource.toResponse(
                                cleanSkill
                        )
                );
            }
        }

        return results;
    }

    /**
     * Returns the complete platform catalog.
     * Useful for the future Learning Resources page.
     */
    public List<LearningResource> getAllResources() {

        return resources.stream()
                .map(
                        resource ->
                                resource.toResponse(
                                        "General"
                                )
                )
                .toList();
    }

    private List<ResourceDefinition>
            createResourceCatalog() {

        List<ResourceDefinition> catalog =
                new ArrayList<>();

        /*
         * 1. Open Culture
         */
        catalog.add(
                new ResourceDefinition(
                        "Open Culture",
                        "Free learning resources from universities and other providers.",
                        "https://www.openculture.com/freeonlinecourses",
                        "Free learning; certificate depends on the course provider.",
                        "Certificate varies",
                        List.of(
                                "java",
                                "python",
                                "programming",
                                "database",
                                "sql",
                                "cloud",
                                "artificial intelligence",
                                "machine learning",
                                "cybersecurity",
                                "web development"
                        ),
                        7,
                        true
                )
        );

        /*
         * 2. The Odin Project
         */
        catalog.add(
                new ResourceDefinition(
                        "The Odin Project",
                        "Project-based web development curriculum.",
                        "https://www.theodinproject.com/",
                        "Free",
                        "No certificate",
                        List.of(
                                "html",
                                "css",
                                "javascript",
                                "node.js",
                                "nodejs",
                                "git",
                                "github",
                                "web development",
                                "frontend",
                                "backend"
                        ),
                        5,
                        false
                )
        );

        /*
         * 3. LearnVern
         */
        catalog.add(
                new ResourceDefinition(
                        "LearnVern",
                        "Free courses covering programming, software development, testing, Docker, cloud and data topics.",
                        "https://www.learnvern.com/courses",
                        "Free learning",
                        "Certificate availability varies by course.",
                        List.of(
                                "java",
                                "python",
                                "c",
                                "javascript",
                                "sql",
                                "mysql",
                                "docker",
                                "cloud",
                                "software testing",
                                "testing",
                                "spring",
                                "spring boot",
                                "web development",
                                "data science",
                                "machine learning"
                        ),
                        1,
                        false
                )
        );

        /*
         * 4. DigitalDefynd
         */
        catalog.add(
                new ResourceDefinition(
                        "DigitalDefynd",
                        "Course-discovery platform covering technology, AI, cybersecurity and other professional areas.",
                        "https://digitaldefynd.com/",
                        "Varies by course/provider",
                        "Varies; DigitalDefynd also offers its own certificate for course completion.",
                        List.of(
                                "java",
                                "python",
                                "docker",
                                "aws",
                                "azure",
                                "cloud",
                                "artificial intelligence",
                                "machine learning",
                                "cybersecurity",
                                "data science",
                                "sql",
                                "software development",
                                "microservices"
                        ),
                        6,
                        true
                )
        );

        /*
         * 5. Class Central
         */
        catalog.add(
                new ResourceDefinition(
                        "Class Central",
                        "Course search and discovery platform for university and professional courses.",
                        "https://www.classcentral.com/",
                        "Course cost varies; many free options exist.",
                        "Certificate varies by provider and course.",
                        List.of(
                                "java",
                                "python",
                                "c",
                                "c++",
                                "c#",
                                "javascript",
                                "typescript",
                                "html",
                                "css",
                                "react",
                                "angular",
                                "spring boot",
                                "microservices",
                                "mysql",
                                "postgresql",
                                "sql",
                                "docker",
                                "kubernetes",
                                "aws",
                                "azure",
                                "git",
                                "linux",
                                "jenkins",
                                "junit",
                                "testing",
                                "artificial intelligence",
                                "machine learning",
                                "data science",
                                "cybersecurity",
                                "cloud"
                        ),
                        3,
                        true
                )
        );

        /*
         * 6. Great Learning
         */
        catalog.add(
                new ResourceDefinition(
                        "Great Learning Academy",
                        "Free self-paced courses in software development, AI, data, cloud and cybersecurity.",
                        "https://www.mygreatlearning.com/academy",
                        "Free learning",
                        "Certificate available for a fee.",
                        List.of(
                                "java",
                                "python",
                                "sql",
                                "mysql",
                                "docker",
                                "aws",
                                "azure",
                                "cloud",
                                "artificial intelligence",
                                "machine learning",
                                "data science",
                                "cybersecurity",
                                "software development",
                                "testing",
                                "web development",
                                "microservices"
                        ),
                        2,
                        false
                )
        );

        /*
         * 7. Alison
         */
        catalog.add(
                new ResourceDefinition(
                        "Alison",
                        "Self-paced courses and diploma programs across IT and other fields.",
                        "https://alison.com/",
                        "Free learning",
                        "Certificate/diploma status varies; many certificates are paid, with some free digital certificates available.",
                        List.of(
                                "java",
                                "python",
                                "sql",
                                "database",
                                "web development",
                                "javascript",
                                "cloud",
                                "cybersecurity",
                                "data science",
                                "artificial intelligence",
                                "software testing",
                                "project management"
                        ),
                        8,
                        true
                )
        );

        /*
         * 8. Microsoft Learn
         */
        catalog.add(
                new ResourceDefinition(
                        "Microsoft Learn",
                        "Official Microsoft training for cloud, AI, security, development and related technologies.",
                        "https://learn.microsoft.com/training/",
                        "Free training",
                        "Credentials depend on the Microsoft assessment/certification.",
                        List.of(
                                "azure",
                                "cloud",
                                "artificial intelligence",
                                "ai",
                                "c#",
                                "dotnet",
                                ".net",
                                "security",
                                "github",
                                "devops",
                                "ci/cd",
                                "data",
                                "database"
                        ),
                        1,
                        false
                )
        );

        /*
         * 9. IBM SkillsBuild
         */
        catalog.add(
                new ResourceDefinition(
                        "IBM SkillsBuild",
                        "Free learning and hands-on content for AI, data, cybersecurity, cloud and career skills.",
                        "https://skillsbuild.org/",
                        "Free",
                        "Shareable IBM digital credentials are available for eligible learning.",
                        List.of(
                                "artificial intelligence",
                                "ai",
                                "machine learning",
                                "data science",
                                "data analysis",
                                "cybersecurity",
                                "cloud",
                                "devops",
                                "python",
                                "sql"
                        ),
                        2,
                        false
                )
        );

        /*
         * 10. freeCodeCamp
         */
        catalog.add(
                new ResourceDefinition(
                        "freeCodeCamp",
                        "Interactive coding curriculum with hands-on projects and certification tracks.",
                        "https://www.freecodecamp.org/learn/",
                        "Free",
                        "Free verified certifications are available for supported certification tracks.",
                        List.of(
                                "html",
                                "css",
                                "javascript",
                                "python",
                                "sql",
                                "relational databases",
                                "backend development",
                                "apis",
                                "data structures",
                                "algorithms",
                                "web development"
                        ),
                        1,
                        false
                )
        );

        /*
         * 11. Cisco Networking Academy
         */
        catalog.add(
                new ResourceDefinition(
                        "Cisco Networking Academy",
                        "Free technical learning in networking, cybersecurity, Python, IT and related fields.",
                        "https://www.netacad.com/",
                        "Many courses are free",
                        "Digital badges/credentials depend on the course or pathway.",
                        List.of(
                                "networking",
                                "cybersecurity",
                                "python",
                                "linux",
                                "it",
                                "information technology",
                                "security",
                                "network security"
                        ),
                        2,
                        false
                )
        );

        /*
         * 12. Google Skillshop
         */
        catalog.add(
                new ResourceDefinition(
                        "Google Skillshop",
                        "Official Google learning and certification platform, especially useful for Google products, advertising and analytics.",
                        "https://skillshop.withgoogle.com/",
                        "Free training",
                        "Certification availability depends on the specific Google product.",
                        List.of(
                                "google ads",
                                "analytics",
                                "digital marketing",
                                "marketing",
                                "google"
                        ),
                        9,
                        false
                )
        );

        /*
         * 13. AWS Skill Builder
         */
        catalog.add(
                new ResourceDefinition(
                        "AWS Skill Builder",
                        "Official AWS learning platform with free cloud and AI learning resources.",
                        "https://aws.amazon.com/training/digital/",
                        "Free digital training is available",
                        "AWS certifications are separate; some free learning also offers microcredentials.",
                        List.of(
                                "aws",
                                "cloud",
                                "devops",
                                "serverless",
                                "artificial intelligence",
                                "ai",
                                "machine learning",
                                "containers",
                                "docker",
                                "kubernetes",
                                "security",
                                "database"
                        ),
                        1,
                        false
                )
        );

        /*
         * 14. Kaggle Learn
         */
        catalog.add(
                new ResourceDefinition(
                        "Kaggle Learn",
                        "Short practical courses for Python, SQL, data science and machine learning.",
                        "https://www.kaggle.com/learn/",
                        "Free",
                        "Completion certificates are available for Kaggle Learn courses.",
                        List.of(
                                "python",
                                "sql",
                                "machine learning",
                                "artificial intelligence",
                                "data science",
                                "data analysis",
                                "pandas",
                                "numpy",
                                "tensorflow",
                                "deep learning"
                        ),
                        1,
                        false
                )
        );

        /*
         * 15. Oracle Academy
         */
        catalog.add(
                new ResourceDefinition(
                        "Oracle Academy",
                        "Oracle learning resources covering Java, databases, SQL and cloud technologies.",
                        "https://academy.oracle.com/",
                        "Free resources with membership",
                        "Professional certification exams have associated costs.",
                        List.of(
                                "java",
                                "sql",
                                "database",
                                "oracle",
                                "pl/sql",
                                "cloud",
                                "artificial intelligence",
                                "machine learning"
                        ),
                        2,
                        false
                )
        );

        return catalog;
    }

    private String normalize(
            String value) {

        if (value == null) {
            return "";
        }

        return value
                .toLowerCase(Locale.ROOT)
                .replaceAll(
                        "[^a-z0-9+#./-]",
                        " "
                )
                .replaceAll(
                        "\\s+",
                        " "
                )
                .trim();
    }

    private static class ResourceDefinition {

        private final String platform;

        private final String description;

        private final String url;

        private final String learningCost;

        private final String credentialStatus;

        private final List<String> relevantSkills;

        private final int priority;

        private final boolean generalResource;

        ResourceDefinition(
                String platform,
                String description,
                String url,
                String learningCost,
                String credentialStatus,
                List<String> relevantSkills,
                int priority,
                boolean generalResource) {

            this.platform = platform;
            this.description = description;
            this.url = url;
            this.learningCost = learningCost;
            this.credentialStatus = credentialStatus;
            this.relevantSkills = relevantSkills;
            this.priority = priority;
            this.generalResource = generalResource;
        }

        boolean matches(
                String normalizedSkill) {

            if (normalizedSkill == null
                    || normalizedSkill.isBlank()) {

                return false;
            }

            for (String skill :
                    relevantSkills) {

                String normalizedResourceSkill =
                        skill.toLowerCase(
                                Locale.ROOT
                        );

                if (normalizedSkill.equals(
                        normalizedResourceSkill)) {

                    return true;
                }

                /*
                 * Match a broader skill phrase.
                 *
                 * Example:
                 * "spring boot" can match a resource
                 * tagged with "spring boot".
                 */
                if (normalizedSkill.contains(
                        normalizedResourceSkill)
                        ||
                        normalizedResourceSkill.contains(
                                normalizedSkill)) {

                    return true;
                }
            }

            return false;
        }

        boolean isGeneralResource() {

            return generalResource;
        }

        int priority() {

            return priority;
        }

        LearningResource toResponse(
                String skill) {

            return new LearningResource(
                    skill,
                    platform,
                    description,
                    url,
                    learningCost,
                    credentialStatus,
                    determineResourceType()
            );
        }

        private String determineResourceType() {

            if (platform.equals(
                    "Class Central")
                    ||
                    platform.equals(
                            "DigitalDefynd")
                    ||
                    platform.equals(
                            "Open Culture")) {

                return "Course Aggregator";
            }

            return "Learning Platform";
        }
    }
}