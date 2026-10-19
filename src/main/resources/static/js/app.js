const API_BASE_URL = "";

const state = {
    currentSection: "dashboard",
    generatedResumeId: null,
    latestMissingSkills: []
};

const learningCatalogState = {
    allResources: [],
    visibleResources: [],
    activeFilter: "ALL",
    searchText: "",
    personalizedResults: false
};


document.addEventListener(
    "DOMContentLoaded",
    () => {

        initializeNavigation();

        initializeForms();

        initializeFileNames();

        initializeMobileMenu();

        initializeLearningCatalog();

        checkHealth();

        loadDashboardStats();

    }
);


/* =========================================================
   NAVIGATION
   ========================================================= */

function initializeNavigation() {

    document
        .querySelectorAll(".nav-item")
        .forEach(button => {

            button.addEventListener(
                "click",
                () => {

                    openSection(
                        button.dataset.section
                    );

                    closeMobileSidebar();

                }
            );

        });


    document
        .querySelectorAll("[data-open-section]")
        .forEach(button => {

            button.addEventListener(
                "click",
                () => {

                    openSection(
                        button.dataset.openSection
                    );

                }
            );

        });

}


function openSection(
    sectionName
) {

    document
        .querySelectorAll(".app-section")
        .forEach(section => {

            section.classList.remove(
                "active-section"
            );

        });


    const target =
        document.getElementById(
            `section-${sectionName}`
        );


    if (!target) {
        return;
    }


    target.classList.add(
        "active-section"
    );


    document
        .querySelectorAll(".nav-item")
        .forEach(button => {

            button.classList.toggle(
                "active",
                button.dataset.section === sectionName
            );

        });


    const pageInfo =
        getPageInfo(
            sectionName
        );


    document.getElementById(
        "pageTitle"
    ).textContent =
        pageInfo.title;


    document.getElementById(
        "breadcrumbText"
    ).textContent =
        pageInfo.breadcrumb;


    state.currentSection =
        sectionName;


    window.scrollTo(
        {
            top: 0,
            behavior: "smooth"
        }
    );

}


function getPageInfo(
    section
) {

    const pages = {

        dashboard: {
            title: "Career Dashboard",
            breadcrumb: "Dashboard"
        },

        "resume-builder": {
            title: "AI Resume Builder",
            breadcrumb: "Resume Builder"
        },

        "resume-analyzer": {
            title: "Resume Analyzer",
            breadcrumb: "Resume Analyzer"
        },

        "job-matcher": {
            title: "ATS Job Matcher",
            breadcrumb: "Job Matcher"
        },

        interview: {
            title: "Interview Preparation",
            breadcrumb: "Interview Prep"
        },

        career: {
            title: "Career Guidance",
            breadcrumb: "Career Guidance"
        },

        "study-plan": {
            title: "Study Planner",
            breadcrumb: "Study Planner"
        },

        "learning-resources": {
            title: "Learning Resources",
            breadcrumb: "Learning Resources"
        }

    };


    return (
        pages[section]
        || pages.dashboard
    );

}


/* =========================================================
   FORMS
   ========================================================= */

function initializeForms() {

    document
        .getElementById(
            "resumeBuilderForm"
        )
        .addEventListener(
            "submit",
            handleResumeBuilder
        );


    document
        .getElementById(
            "resumeAnalyzerForm"
        )
        .addEventListener(
            "submit",
            handleResumeAnalyzer
        );


    document
        .getElementById(
            "jobMatcherForm"
        )
        .addEventListener(
            "submit",
            handleJobMatcher
        );


    document
        .getElementById(
            "interviewForm"
        )
        .addEventListener(
            "submit",
            handleInterviewPreparation
        );


    document
        .getElementById(
            "careerForm"
        )
        .addEventListener(
            "submit",
            handleCareerGuidance
        );


    document
        .getElementById(
            "studyPlanForm"
        )
        .addEventListener(
            "submit",
            handleStudyPlan
        );


    document
        .getElementById(
            "learningResourceForm"
        )
        .addEventListener(
            "submit",
            handleLearningResources
        );


    document
        .getElementById(
            "downloadResumeButton"
        )
        .addEventListener(
            "click",
            downloadGeneratedResume
        );


    document
        .getElementById(
            "healthButton"
        )
        .addEventListener(
            "click",
            checkHealth
        );

    document.getElementById("resourceCatalogSearch")
        .addEventListener("input", event => {
            learningCatalogState.searchText = event.target.value.trim().toLowerCase();
            applyLearningCatalogFilters();
        });

    document.querySelectorAll("[data-resource-filter]")
        .forEach(button => button.addEventListener("click", () => {
            learningCatalogState.activeFilter = button.dataset.resourceFilter;
            document.querySelectorAll("[data-resource-filter]").forEach(item => {
                item.classList.toggle("active", item === button);
            });
            applyLearningCatalogFilters();
        }));

    document.getElementById("browseAllResources")
        .addEventListener("click", () => {
            learningCatalogState.personalizedResults = false;
            learningCatalogState.visibleResources = learningCatalogState.allResources;
            learningCatalogState.activeFilter = "ALL";
            document.querySelectorAll("[data-resource-filter]").forEach(item => {
                item.classList.toggle("active", item.dataset.resourceFilter === "ALL");
            });
            document.getElementById("learningSkills").value = "";
            document.getElementById("resourceCatalogSearch").value = "";
            learningCatalogState.searchText = "";
            applyLearningCatalogFilters();
        });

}


/* =========================================================
   FILE INPUTS
   ========================================================= */

function initializeFileNames() {

    setupFileName(
        "builderResumeFile",
        "builderFileName"
    );

    setupFileName(
        "analyzerResumeFile",
        "analyzerFileName"
    );

    setupFileName(
        "jobResumeFile",
        "jobFileName"
    );

    setupFileName(
        "interviewResumeFile",
        "interviewFileName"
    );

    setupFileName(
        "careerResumeFile",
        "careerFileName"
    );

    setupFileName(
        "studyResumeFile",
        "studyFileName"
    );

}


function setupFileName(
    inputId,
    outputId
) {

    const input =
        document.getElementById(
            inputId
        );

    const output =
        document.getElementById(
            outputId
        );


    if (!input || !output) {
        return;
    }


    input.addEventListener(
        "change",
        () => {

            if (!input.files.length) {

                output.textContent =
                    "No file selected";

                return;
            }


            output.textContent =
                input.files[0].name;

        }
    );

}


/* =========================================================
   MOBILE MENU
   ========================================================= */

function initializeMobileMenu() {

    const menuButton =
        document.getElementById(
            "mobileMenuButton"
        );


    if (menuButton) {

        menuButton.addEventListener(
            "click",
            () => {

                document
                    .getElementById("sidebar")
                    .classList.toggle(
                        "mobile-open"
                    );

            }
        );

    }


    const profileButton =
        document.getElementById(
            "mobileProfileButton"
        );


    if (profileButton) {

        profileButton.addEventListener(
            "click",
            () => {

                showToast(
                    "AI Career Companion",
                    "Your career workspace is active."
                );

            }
        );

    }

}


function closeMobileSidebar() {

    const sidebar =
        document.getElementById(
            "sidebar"
        );


    if (sidebar) {

        sidebar.classList.remove(
            "mobile-open"
        );

    }

}


/* =========================================================
   RESUME BUILDER
   ========================================================= */

async function handleResumeBuilder(
    event
) {

    event.preventDefault();


    const file =
        document.getElementById(
            "builderResumeFile"
        ).files[0];


    const targetRole =
        document.getElementById(
            "builderTargetRole"
        ).value.trim();


    const jobDescription =
        document.getElementById(
            "builderJobDescription"
        ).value.trim();


    if (!validatePdf(file)) {
        return;
    }


    if (!targetRole) {

        showToast(
            "Target role required",
            "Enter the job role you want to target."
        );

        return;
    }


    const formData =
        new FormData();


    formData.append(
        "file",
        file
    );


    formData.append(
        "targetRole",
        targetRole
    );


    if (jobDescription) {

        formData.append(
            "jobDescription",
            jobDescription
        );

    }


    setLoading(
        "builderSubmitButton",
        true,
        "Building..."
    );


    showElement(
        "builderLoading"
    );


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/resume-builder/generate`,
                {
                    method: "POST",
                    body: formData
                }
            );


        const data =
            await parseResponse(
                response
            );


        state.generatedResumeId =
            data.id;


        renderResumeBuilderResult(
            data
        );


        loadDashboardStats();


        showToast(
            "Resume created",
            "Your optimized resume is ready."
        );

    } catch (error) {

        showToast(
            "Resume Builder error",
            error.message
        );

    } finally {

        setLoading(
            "builderSubmitButton",
            false,
            "Generate Optimized Resume"
        );

        hideElement(
            "builderLoading"
        );

    }

}


/* =========================================================
   RESUME ANALYZER
   ========================================================= */

async function handleResumeAnalyzer(
    event
) {

    event.preventDefault();


    const file =
        document.getElementById(
            "analyzerResumeFile"
        ).files[0];


    if (!validatePdf(file)) {
        return;
    }


    const formData =
        new FormData();


    formData.append(
        "file",
        file
    );


    const button =
        event.target.querySelector(
            "button[type='submit']"
        );


    setLoading(
        button,
        true,
        "Analyzing..."
    );


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/resumes/analyze`,
                {
                    method: "POST",
                    body: formData
                }
            );


        const data =
            await parseResponse(
                response
            );


        renderResumeAnalysis(
            data
        );


        loadDashboardStats();


        showToast(
            "Analysis complete",
            "Your resume has been analyzed."
        );

    } catch (error) {

        showToast(
            "Resume Analyzer error",
            error.message
        );

    } finally {

        setLoading(
            button,
            false,
            "Analyze Resume"
        );

    }

}


/* =========================================================
   JOB MATCHER
   ========================================================= */

async function handleJobMatcher(
    event
) {

    event.preventDefault();


    const file =
        document.getElementById(
            "jobResumeFile"
        ).files[0];


    const jobTitle =
        document.getElementById(
            "jobTitle"
        ).value.trim();


    const jobDescription =
        document.getElementById(
            "jobDescription"
        ).value.trim();


    if (!validatePdf(file)) {
        return;
    }


    if (!jobTitle || !jobDescription) {

        showToast(
            "Missing information",
            "Enter both the job title and job description."
        );

        return;
    }


    const formData =
        new FormData();


    formData.append(
        "file",
        file
    );


    formData.append(
        "jobTitle",
        jobTitle
    );


    formData.append(
        "jobDescription",
        jobDescription
    );


    const button =
        event.target.querySelector(
            "button[type='submit']"
        );


    setLoading(
        button,
        true,
        "Analyzing..."
    );


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/resumes/match`,
                {
                    method: "POST",
                    body: formData
                }
            );


        const data =
            await parseResponse(
                response
            );


        renderJobMatch(
            data
        );


        state.latestMissingSkills =
            [
                ...safeArray(
                    data.missingRequiredSkills
                ),

                ...safeArray(
                    data.missingPreferredSkills
                )
            ];


        loadDashboardStats();


        showToast(
            "ATS analysis complete",
            "Your resume has been compared with the job."
        );

    } catch (error) {

        showToast(
            "Job Matcher error",
            error.message
        );

    } finally {

        setLoading(
            button,
            false,
            "Analyze Job Match"
        );

    }

}


/* =========================================================
   INTERVIEW
   ========================================================= */

async function handleInterviewPreparation(
    event
) {

    event.preventDefault();


    const file =
        document.getElementById(
            "interviewResumeFile"
        ).files[0];


    const targetRole =
        document.getElementById(
            "interviewTargetRole"
        ).value.trim();


    if (!validatePdf(file)) {
        return;
    }


    if (!targetRole) {

        showToast(
            "Target role required",
            "Enter the role you are preparing for."
        );

        return;
    }


    const formData =
        new FormData();


    formData.append(
        "file",
        file
    );


    formData.append(
        "targetRole",
        targetRole
    );


    const button =
        event.target.querySelector(
            "button[type='submit']"
        );


    setLoading(
        button,
        true,
        "Generating..."
    );


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/interview/prepare`,
                {
                    method: "POST",
                    body: formData
                }
            );


        const data =
            await parseResponse(
                response
            );


        renderInterview(
            data
        );


        showToast(
            "Interview prep ready",
            "Personalized questions have been generated."
        );

    } catch (error) {

        showToast(
            "Interview preparation error",
            error.message
        );

    } finally {

        setLoading(
            button,
            false,
            "Generate Interview Prep"
        );

    }

}


/* =========================================================
   CAREER GUIDANCE
   ========================================================= */

async function handleCareerGuidance(
    event
) {

    event.preventDefault();


    const file =
        document.getElementById(
            "careerResumeFile"
        ).files[0];


    const targetRole =
        document.getElementById(
            "careerTargetRole"
        ).value.trim();


    if (!validatePdf(file)) {
        return;
    }


    if (!targetRole) {

        showToast(
            "Target role required",
            "Enter your target role."
        );

        return;
    }


    const formData =
        new FormData();


    formData.append(
        "file",
        file
    );


    formData.append(
        "targetRole",
        targetRole
    );


    const button =
        event.target.querySelector(
            "button[type='submit']"
        );


    setLoading(
        button,
        true,
        "Generating..."
    );


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/career/guidance`,
                {
                    method: "POST",
                    body: formData
                }
            );


        const data =
            await parseResponse(
                response
            );


        renderCareerGuidance(
            data
        );


        showToast(
            "Career roadmap ready",
            "Your guidance has been generated."
        );

    } catch (error) {

        showToast(
            "Career guidance error",
            error.message
        );

    } finally {

        setLoading(
            button,
            false,
            "Generate Career Guidance"
        );

    }

}


/* =========================================================
   STUDY PLAN
   ========================================================= */

async function handleStudyPlan(
    event
) {

    event.preventDefault();


    const file =
        document.getElementById(
            "studyResumeFile"
        ).files[0];


    const targetRole =
        document.getElementById(
            "studyTargetRole"
        ).value.trim();


    if (!validatePdf(file)) {
        return;
    }


    if (!targetRole) {

        showToast(
            "Target role required",
            "Enter your target role."
        );

        return;
    }


    const formData =
        new FormData();


    formData.append(
        "file",
        file
    );


    formData.append(
        "targetRole",
        targetRole
    );


    const button =
        event.target.querySelector(
            "button[type='submit']"
        );


    setLoading(
        button,
        true,
        "Generating..."
    );


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/study-plan/generate`,
                {
                    method: "POST",
                    body: formData
                }
            );


        const data =
            await parseResponse(
                response
            );


        renderStudyPlan(
            data
        );


        showToast(
            "Study plan ready",
            "Your learning plan has been generated."
        );

    } catch (error) {

        showToast(
            "Study planner error",
            error.message
        );

    } finally {

        setLoading(
            button,
            false,
            "Generate Study Plan"
        );

    }

}


/* =========================================================
   LEARNING RESOURCES
   ========================================================= */

async function handleLearningResources(
    event
) {

    event.preventDefault();


    const input =
        document.getElementById(
            "learningSkills"
        );


    const skills =
        input.value
            .split(",")
            .map(
                skill =>
                    skill.trim()
            )
            .filter(
                Boolean
            );


    if (!skills.length) {

        showToast(
            "Enter a skill",
            "Add at least one skill."
        );

        return;
    }


    const button =
        event.target.querySelector(
            "button[type='submit']"
        );


    setLoading(
        button,
        true,
        "Finding..."
    );


    try {

        const query =
            encodeURIComponent(
                skills.join(",")
            );


        const response =
            await fetch(
                `${API_BASE_URL}/api/learning-resources?skills=${query}`
            );


        const data =
            await parseResponse(
                response
            );


        learningCatalogState.personalizedResults = true;
        learningCatalogState.visibleResources = deduplicateLearningResources(data);
        learningCatalogState.activeFilter = "ALL";
        document.querySelectorAll("[data-resource-filter]").forEach(item => {
            item.classList.toggle("active", item.dataset.resourceFilter === "ALL");
        });
        learningCatalogState.searchText = "";
        document.getElementById("resourceCatalogSearch").value = "";
        document.getElementById("browseAllResources").classList.remove("d-none");
        applyLearningCatalogFilters();

    } catch (error) {

        showToast(
            "Learning resource error",
            error.message
        );

    } finally {

        setLoading(
            button,
            false,
            "Find matching resources →"
        );

    }

}


/* =========================================================
   DOWNLOAD RESUME
   ========================================================= */

async function downloadGeneratedResume() {

    if (!state.generatedResumeId) {

        showToast(
            "No resume available",
            "Generate a resume first."
        );

        return;
    }


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/resume-builder/${state.generatedResumeId}/pdf`
            );


        if (!response.ok) {

            throw new Error(
                await extractError(
                    response
                )
            );

        }


        const blob =
            await response.blob();


        const url =
            URL.createObjectURL(
                blob
            );


        const anchor =
            document.createElement(
                "a"
            );


        anchor.href =
            url;


        anchor.download =
            "optimized-resume.pdf";


        document.body.appendChild(
            anchor
        );


        anchor.click();


        anchor.remove();


        URL.revokeObjectURL(
            url
        );


        showToast(
            "Download started",
            "Your optimized resume PDF is ready."
        );

    } catch (error) {

        showToast(
            "Download error",
            error.message
        );

    }

}


/* =========================================================
   RESUME BUILDER RESULT
   ========================================================= */

function renderResumeBuilderResult(
    data
) {

    hideElement(
        "builderEmptyState"
    );


    showElement(
        "builderResult"
    );


    const resume =
        data.resume || {};


    document.getElementById(
        "builderResultTitle"
    ).textContent =
        resume.fullName
        || "Optimized Resume";


    document.getElementById(
        "resumePreview"
    ).innerHTML =
        renderResumePreview(
            resume
        );


    document.getElementById(
        "builderAtsSummary"
    ).innerHTML =
        renderBuilderAts(
            data.atsAnalysis,
            data.atsMessage
        );

}


function renderResumePreview(
    resume
) {

    const skills =
        safeArray(
            resume.skills
        );


    const experience =
        safeArray(
            resume.experience
        );


    const projects =
        safeArray(
            resume.projects
        );


    const education =
        safeArray(
            resume.education
        );


    const certifications =
        safeArray(
            resume.certifications
        );


    const achievements =
        safeArray(
            resume.achievements
        );


    const languages =
        safeArray(
            resume.languages
        );


    const contact =
        [
            resume.email,
            resume.phone,
            resume.location,
            resume.linkedin,
            resume.github,
            resume.portfolio
        ]
        .filter(Boolean)
        .join(" | ");


    let html = "";


    html += `
        <div class="resume-preview-header">

            <h4 class="resume-preview-name">
                ${escapeHtml(
                    resume.fullName
                    || "Candidate"
                )}
            </h4>

            <div class="resume-preview-contact">
                ${escapeHtml(contact)}
            </div>

        </div>
    `;


    if (resume.professionalSummary) {

        html += `
            <div class="resume-preview-section">

                <div class="resume-preview-section-title">
                    PROFESSIONAL SUMMARY
                </div>

                <div class="resume-preview-summary">
                    ${escapeHtml(
                        resume.professionalSummary
                    )}
                </div>

            </div>
        `;

    }


    if (skills.length) {

        html += `
            <div class="resume-preview-section">

                <div class="resume-preview-section-title">
                    TECHNICAL SKILLS
                </div>

                <div class="resume-inline-text">
                    ${skills
                        .map(
                            escapeHtml
                        )
                        .join(" • ")}
                </div>

            </div>
        `;

    }


    if (experience.length) {

        html += `
            <div class="resume-preview-section">

                <div class="resume-preview-section-title">
                    EXPERIENCE
                </div>
        `;


        experience.forEach(
            item => {

                const dates =
                    [
                        item.startDate,
                        item.endDate
                    ]
                    .filter(Boolean)
                    .join(" - ");


                const meta =
                    [
                        item.company,
                        item.location,
                        dates
                    ]
                    .filter(Boolean)
                    .join(" | ");


                html += `
                    <div class="resume-item">

                        <div class="resume-item-title">
                            ${escapeHtml(
                                item.jobTitle
                                || "Experience"
                            )}
                        </div>

                        <div class="resume-item-meta">
                            ${escapeHtml(meta)}
                        </div>
                `;


                const responsibilities =
                    safeArray(
                        item.responsibilities
                    );


                if (responsibilities.length) {

                    html += "<ul>";


                    responsibilities.forEach(
                        point => {

                            html += `
                                <li>
                                    ${escapeHtml(point)}
                                </li>
                            `;

                        }
                    );


                    html += "</ul>";

                }


                html += `
                    </div>
                `;

            }
        );


        html += `
            </div>
        `;

    }


    if (projects.length) {

        html += `
            <div class="resume-preview-section">

                <div class="resume-preview-section-title">
                    PROJECTS
                </div>
        `;


        projects.forEach(
            item => {

                html += `
                    <div class="resume-item">

                        <div class="resume-item-title">
                            ${escapeHtml(
                                item.name
                                || "Project"
                            )}
                        </div>

                        <div class="resume-item-meta">
                            ${escapeHtml(
                                safeArray(
                                    item.technologies
                                ).join(", ")
                            )}
                        </div>
                `;


                if (item.description) {

                    html += `
                        <div class="resume-inline-text">
                            ${escapeHtml(
                                item.description
                            )}
                        </div>
                    `;

                }


                const contributions =
                    safeArray(
                        item.contributions
                    );


                if (contributions.length) {

                    html += "<ul>";


                    contributions.forEach(
                        point => {

                            html += `
                                <li>
                                    ${escapeHtml(point)}
                                </li>
                            `;

                        }
                    );


                    html += "</ul>";

                }


                html += `
                    </div>
                `;

            }
        );


        html += `
            </div>
        `;

    }


    if (education.length) {

        html += `
            <div class="resume-preview-section">

                <div class="resume-preview-section-title">
                    EDUCATION
                </div>
        `;


        education.forEach(
            item => {

                const details =
                    [
                        item.institution,
                        item.location,

                        item.startDate
                        && item.endDate
                            ? `${item.startDate} - ${item.endDate}`
                            : item.startDate
                            || item.endDate,

                        item.grade
                    ]
                    .filter(Boolean)
                    .join(" | ");


                html += `
                    <div class="resume-item">

                        <div class="resume-item-title">
                            ${escapeHtml(
                                item.degree
                                || "Education"
                            )}
                        </div>

                        <div class="resume-item-meta">
                            ${escapeHtml(details)}
                        </div>

                    </div>
                `;

            }
        );


        html += `
            </div>
        `;

    }


    html += renderSimpleResumeSection(
        "CERTIFICATIONS",
        certifications
    );


    html += renderSimpleResumeSection(
        "ACHIEVEMENTS",
        achievements
    );


    html += renderSimpleResumeSection(
        "LANGUAGES",
        languages
    );


    return html;
}


function renderSimpleResumeSection(
    title,
    items
) {

    if (!items.length) {
        return "";
    }


    return `
        <div class="resume-preview-section">

            <div class="resume-preview-section-title">
                ${escapeHtml(title)}
            </div>

            <ul>
                ${items
                    .map(
                        item => `
                            <li>
                                ${escapeHtml(item)}
                            </li>
                        `
                    )
                    .join("")}
            </ul>

        </div>
    `;

}


/* =========================================================
   ATS BUILDER RESULT
   ========================================================= */

function renderBuilderAts(
    ats,
    message
) {

    if (!ats) {

        return `
            <div class="ats-summary-top">

                <div>

                    <strong>
                        ATS analysis pending
                    </strong>

                    <span>
                        ${escapeHtml(
                            message
                            || "Add a job description for job-specific ATS analysis."
                        )}
                    </span>

                </div>

            </div>
        `;

    }


    return `
        <div class="ats-summary-top">

            <div>

                <strong>
                    ${numberValue(ats.score)}/100
                </strong>

                <span>
                    ATS alignment score
                </span>

            </div>

            <span>
                Job-targeted
            </span>

        </div>


        <div class="score-bars mt-3">

            ${renderScoreBar(
                "Required Skills",
                ats.requiredSkillScore,
                40
            )}

            ${renderScoreBar(
                "Preferred Skills",
                ats.preferredSkillScore,
                10
            )}

            ${renderScoreBar(
                "Keywords",
                ats.keywordScore,
                20
            )}

            ${renderScoreBar(
                "Role Relevance",
                ats.relevanceScore,
                10
            )}

            ${renderScoreBar(
                "Structure",
                ats.structureScore,
                10
            )}

            ${renderScoreBar(
                "Content",
                ats.contentScore,
                10
            )}

        </div>


        ${renderSkillGroup(
            "Missing Required Skills",
            ats.missingRequiredSkills,
            "danger"
        )}


        ${renderSkillGroup(
            "Missing Preferred Skills",
            ats.missingPreferredSkills,
            "warning"
        )}


        ${renderTextListSection(
            "Suggestions",
            ats.suggestions
        )}

    `;

}


/* =========================================================
   RESUME ANALYSIS
   ========================================================= */

function renderResumeAnalysis(
    data
) {

    hideElement(
        "analyzerEmptyState"
    );


    showElement(
        "analyzerResult"
    );


    document.getElementById(
        "analyzerResult"
    ).innerHTML = `

        <div class="score-layout">

            <div class="score-circle">

                <strong>
                    ${numberValue(data.score)}
                </strong>

                <span>
                    Resume Score
                </span>

            </div>

            <div class="score-bars">

                ${renderScoreBar(
                    "Resume Quality",
                    numberValue(data.score),
                    100
                )}

            </div>

        </div>


        ${renderTagSection(
            "Detected Skills",
            data.skills,
            "success"
        )}


        ${renderTagSection(
            "Missing / Improvement Areas",
            data.missingSkills,
            "warning"
        )}


        ${renderTextListSection(
            "Strengths",
            data.strengths
        )}


        ${renderTextListSection(
            "Weaknesses",
            data.weaknesses
        )}


        ${renderTextListSection(
            "Suggestions",
            data.suggestions
        )}

    `;

}


/* =========================================================
   JOB MATCH RESULT
   ========================================================= */

function renderJobMatch(
    data
) {

    hideElement(
        "jobEmptyState"
    );


    showElement(
        "jobResult"
    );


    document.getElementById(
        "jobResult"
    ).innerHTML = `

        <div class="score-layout">

            <div class="score-circle">

                <strong>
                    ${numberValue(data.matchScore)}
                </strong>

                <span>
                    ATS Score
                </span>

            </div>

            <div class="score-bars">

                ${renderScoreBar(
                    "Required Skills",
                    data.requiredSkillScore,
                    40
                )}

                ${renderScoreBar(
                    "Preferred Skills",
                    data.preferredSkillScore,
                    10
                )}

                ${renderScoreBar(
                    "Keywords",
                    data.keywordScore,
                    20
                )}

                ${renderScoreBar(
                    "Role Relevance",
                    data.relevanceScore,
                    10
                )}

                ${renderScoreBar(
                    "Structure",
                    data.structureScore,
                    10
                )}

                ${renderScoreBar(
                    "Content",
                    data.contentScore,
                    10
                )}

            </div>

        </div>


        ${renderTagSection(
            "Matched Required Skills",
            data.matchedRequiredSkills,
            "success"
        )}


        ${renderTagSection(
            "Missing Required Skills",
            data.missingRequiredSkills,
            "danger"
        )}


        ${renderTagSection(
            "Matched Preferred Skills",
            data.matchedPreferredSkills,
            "success"
        )}


        ${renderTagSection(
            "Missing Preferred Skills",
            data.missingPreferredSkills,
            "warning"
        )}


        ${renderTagSection(
            "Matched Keywords",
            data.matchingKeywords,
            "success"
        )}


        ${renderTagSection(
            "Missing Keywords",
            data.missingKeywords,
            "warning"
        )}


        ${
            data.aiSummary
                ? `
                    <div class="result-section">

                        <div class="result-section-title">
                            AI Summary
                        </div>

                        <p class="resume-preview-summary">
                            ${escapeHtml(
                                data.aiSummary
                            )}
                        </p>

                    </div>
                `
                : ""
        }


        ${renderTextListSection(
            "AI Recommendations",
            data.aiRecommendations
        )}


        ${renderTextListSection(
            "AI Improvements",
            data.aiImprovements
        )}


        <div class="result-section">

            <button
                class="primary-button"
                type="button"
                id="learningFromMatchButton">

                Find Learning Resources

            </button>

        </div>

    `;


    const learningButton =
        document.getElementById(
            "learningFromMatchButton"
        );


    if (learningButton) {

        learningButton.addEventListener(
            "click",
            openLearningFromMissingSkills
        );

    }

}


/* =========================================================
   OPEN LEARNING FROM ATS
   ========================================================= */

function openLearningFromMissingSkills() {

    const skills =
        safeArray(
            state.latestMissingSkills
        );


    openSection(
        "learning-resources"
    );


    const input =
        document.getElementById(
            "learningSkills"
        );


    input.value =
        skills.join(", ");


    if (skills.length) {

        document
            .getElementById(
                "learningResourceForm"
            )
            .requestSubmit();

    }

}


/* =========================================================
   INTERVIEW RESULT
   ========================================================= */

function renderInterview(
    data
) {

    hideElement(
        "interviewEmptyState"
    );


    showElement(
        "interviewResult"
    );


    let html = "";


    if (data.targetRole) {

        html += `
            <div class="result-section">

                <span class="result-label">
                    TARGET ROLE
                </span>

                <h3 class="mt-1">
                    ${escapeHtml(
                        data.targetRole
                    )}
                </h3>

            </div>
        `;

    }


    html += renderQuestionSection(
        "Technical Questions",
        data.technicalQuestions
    );


    html += renderQuestionSection(
        "Project Questions",
        data.projectQuestions
    );


    html += renderQuestionSection(
        "HR Questions",
        data.hrQuestions
    );


    document.getElementById(
        "interviewResult"
    ).innerHTML =
        html;

}


function renderQuestionSection(
    title,
    questions
) {

    const list =
        safeArray(
            questions
        );


    if (!list.length) {
        return "";
    }


    return `
        <div class="result-section">

            <div class="result-section-title">
                ${escapeHtml(title)}
            </div>

            <div class="question-grid">

                ${list
                    .map(
                        (item, index) =>
                            renderQuestion(
                                item,
                                index
                            )
                    )
                    .join("")}

            </div>

        </div>
    `;

}


function renderQuestion(
    item,
    index
) {

    if (typeof item === "string") {

        return `
            <div class="question-card">

                <div class="question-number">
                    QUESTION ${index + 1}
                </div>

                <div class="question-text">
                    ${escapeHtml(item)}
                </div>

            </div>
        `;

    }


    return `
        <div class="question-card">

            <div class="question-top">

                <span class="question-number">
                    QUESTION ${index + 1}
                </span>

                <div class="question-meta">

                    ${
                        item.category
                            ? `
                                <span class="meta-pill">
                                    ${escapeHtml(
                                        item.category
                                    )}
                                </span>
                            `
                            : ""
                    }

                    ${
                        item.difficulty
                            ? `
                                <span class="meta-pill">
                                    ${escapeHtml(
                                        item.difficulty
                                    )}
                                </span>
                            `
                            : ""
                    }

                </div>

            </div>


            <div class="question-text">
                ${escapeHtml(
                    item.question
                    || item.text
                    || ""
                )}
            </div>


            ${
                item.expectedAnswer
                    ? `
                        <div class="resume-preview-summary mt-2">

                            <strong>
                                Expected answer focus:
                            </strong>

                            ${escapeHtml(
                                item.expectedAnswer
                            )}

                        </div>
                    `
                    : ""
            }

        </div>
    `;

}


/* =========================================================
   CAREER RESULT
   ========================================================= */

function renderCareerGuidance(
    data
) {

    hideElement(
        "careerEmptyState"
    );


    showElement(
        "careerResult"
    );


    document.getElementById(
        "careerResult"
    ).innerHTML = `

        ${
            data.profileSummary
                ? `
                    <div class="result-section">

                        <div class="result-section-title">
                            Profile Summary
                        </div>

                        <p class="resume-preview-summary">
                            ${escapeHtml(
                                data.profileSummary
                            )}
                        </p>

                    </div>
                `
                : ""
        }


        ${renderTagSection(
            "Recommended Roles",
            data.recommendedRoles,
            "success"
        )}


        ${renderTagSection(
            "Skill Gaps",
            data.skillGaps,
            "danger"
        )}


        ${renderTagSection(
            "Skills to Learn",
            data.skillsToLearn,
            "warning"
        )}


        ${renderTextListSection(
            "Recommended Projects",
            data.recommendedProjects
        )}


        ${renderRoadmap(
            "Action Plan",
            data.actionPlan
        )}

    `;

}


/* =========================================================
   STUDY PLAN RESULT
   ========================================================= */

function renderStudyPlan(
    data
) {

    hideElement(
        "studyEmptyState"
    );


    showElement(
        "studyResult"
    );


    document.getElementById(
        "studyResult"
    ).innerHTML = `

        <div class="result-section">

            <span class="result-label">
                TARGET ROLE
            </span>

            <h3 class="mt-1">
                ${escapeHtml(
                    data.targetRole
                    || "Study Plan"
                )}
            </h3>

        </div>


        ${
            data.overallGoal
                ? `
                    <div class="result-section">

                        <div class="result-section-title">
                            Overall Goal
                        </div>

                        <p class="resume-preview-summary">
                            ${escapeHtml(
                                data.overallGoal
                            )}
                        </p>

                    </div>
                `
                : ""
        }


        <div class="stats-grid">

            <div class="metric-card">

                <div class="metric-icon">
                    L
                </div>

                <div>

                    <span class="metric-label">
                        Current Level
                    </span>

                    <strong>
                        ${escapeHtml(
                            data.currentLevel
                            || "—"
                        )}
                    </strong>

                </div>

            </div>


            <div class="metric-card">

                <div class="metric-icon">
                    W
                </div>

                <div>

                    <span class="metric-label">
                        Estimated Weeks
                    </span>

                    <strong>
                        ${numberValue(
                            data.estimatedWeeks
                        )}
                    </strong>

                </div>

            </div>

        </div>


        ${renderTagSection(
            "Topics",
            data.topics,
            "success"
        )}


        ${renderTextListSection(
            "Weekly Plan",
            data.weeklyPlan
        )}


        ${renderTextListSection(
            "Recommended Projects",
            data.recommendedProjects
        )}

    `;

}


/* =========================================================
   LEARNING RESOURCES
   ========================================================= */

function initializeLearningCatalog() {

    fetch(`${API_BASE_URL}/api/learning-resources/all`)
        .then(parseResponse)
        .then(resources => {
            learningCatalogState.allResources = deduplicateLearningResources(resources);
            learningCatalogState.visibleResources = learningCatalogState.allResources;
            applyLearningCatalogFilters();
        })
        .catch(() => {
            const summary = document.getElementById("resourceResultSummary");
            if (summary) summary.textContent = "Learning catalog could not be loaded. Check that the application server is running.";
            renderLearningResources([]);
        });
}


function deduplicateLearningResources(resources) {

    if (!Array.isArray(resources)) return [];

    const byPlatform = new Map();

    resources.forEach(resource => {
        if (!resource || !resource.platform) return;
        const key = String(resource.platform).trim().toLowerCase();
        if (!byPlatform.has(key)) {
            byPlatform.set(key, { ...resource, _matchedSkills: new Set([resource.skill].filter(Boolean)) });
        } else {
            const existing = byPlatform.get(key);
            if (resource.skill) existing._matchedSkills.add(resource.skill);
        }
    });

    return Array.from(byPlatform.values()).map(resource => ({
        ...resource,
        skill: Array.from(resource._matchedSkills || []).join(" · ") || resource.skill || "General",
        _matchedSkills: undefined
    }));
}


function applyLearningCatalogFilters() {

    const source = learningCatalogState.visibleResources || [];
    const search = learningCatalogState.searchText;
    const filter = learningCatalogState.activeFilter;

    const matches = source.filter(resource => {
        const searchable = [
            resource.platform,
            resource.description,
            resource.skill,
            Array.isArray(resource.skills) ? resource.skills.join(" ") : "",
            resource.learningCost,
            resource.credentialStatus,
            resource.resourceType
        ].filter(Boolean).join(" ").toLowerCase();

        const matchesSearch = !search || searchable.includes(search);
        const credentialType = resource.credentialType || inferCredentialType(resource);
        const matchesFilter = filter === "ALL"
            || (filter === "FREE_LEARNING_PAID_EXAM"
                ? ["FREE_LEARNING_PAID_EXAM", "FREE_AUDIT_PAID_CERTIFICATE"].includes(credentialType)
                : credentialType === filter);

        return matchesSearch && matchesFilter;
    });

    renderLearningResources(matches);

    const summary = document.getElementById("resourceResultSummary");
    if (summary) {
        const countLabel = matches.length === 1 ? "result" : "results";
        const sourceLabel = learningCatalogState.personalizedResults ? "matched" : "in the directory";
        summary.textContent = `${matches.length} ${sourceLabel} ${countLabel}`;
    }

    const browseButton = document.getElementById("browseAllResources");
    if (browseButton) browseButton.classList.toggle("d-none", !learningCatalogState.personalizedResults);
}


function inferCredentialType(resource) {

    const platform = String(resource.platform || "").toLowerCase();
    const status = String(resource.credentialStatus || "").toLowerCase();

    if (["freecodecamp", "kaggle learn", "simplilearn skillup", "hubspot academy", "google skillshop"].includes(platform)) return "FREE_CERTIFICATE";
    if (["ibm skillsbuild", "salesforce trailhead"].includes(platform)) return "FREE_BADGE";
    if (platform === "the odin project") return "NO_CERTIFICATE";
    if (platform === "great learning academy") return "PAID_CERTIFICATE";
    if (status.includes("usually paid") || status.includes("audit does not include")) return "FREE_AUDIT_PAID_CERTIFICATE";
    if (status.includes("exam") && (status.includes("paid") || status.includes("cost"))) return "FREE_LEARNING_PAID_EXAM";
    return "VARIES";
}


function renderLearningResources(resources) {

    const container = document.getElementById("learningResourceResults");

    if (!Array.isArray(resources) || !resources.length) {
        container.innerHTML = `
            <div class="empty-state full-span resource-empty-state">
                <div class="empty-icon" aria-hidden="true">⌕</div>
                <h3>No platforms match these filters</h3>
                <p>Try another keyword or choose “All resources” to see the whole catalog.</p>
            </div>`;
        return;
    }

    container.innerHTML = resources.map(renderResourceCard).join("");
}


function renderResourceCard(resource) {

    const credentialType = resource.credentialType || inferCredentialType(resource);
    const credentialLabels = {
        FREE_CERTIFICATE: { label: "Free certificate", className: "credential-free" },
        FREE_BADGE: { label: "Free badge / credential", className: "credential-badge" },
        FREE_LEARNING_PAID_EXAM: { label: "Free learning · paid exam", className: "credential-paid" },
        FREE_AUDIT_PAID_CERTIFICATE: { label: "Free audit · paid certificate", className: "credential-paid" },
        PAID_CERTIFICATE: { label: "Certificate may cost", className: "credential-paid" },
        NO_CERTIFICATE: { label: "No certificate", className: "credential-neutral" },
        VARIES: { label: "Varies by course", className: "credential-varies" }
    };
    const credential = credentialLabels[credentialType] || credentialLabels.VARIES;
    const url = safeUrl(resource.url);
    const skillLabels = (Array.isArray(resource.skills) && resource.skills.length)
        ? resource.skills.slice(0, 3)
        : String(resource.skill || "General skills").split(/[·,]/).map(value => value.trim()).filter(Boolean).slice(0, 3);
    const remainingSkillCount = Math.max(0, (Array.isArray(resource.skills) && resource.skills.length)
        ? resource.skills.length - skillLabels.length
        : String(resource.skill || "").split(/[·,]/).filter(Boolean).length - skillLabels.length);
    const skillChips = skillLabels.map(skill => `<span class="resource-tag">${escapeHtml(skill)}</span>`).join("")
        + (remainingSkillCount > 0 ? `<span class="resource-tag resource-tag-more">+${remainingSkillCount} more</span>` : "");

    return `
        <article class="resource-card">
            <div class="resource-card-topline">
                <div class="resource-monogram" aria-hidden="true">${escapeHtml(String(resource.platform || "L").trim().charAt(0).toUpperCase())}</div>
                <span class="resource-type">${escapeHtml(resource.resourceType || "Learning platform")}</span>
            </div>
            <h4>${escapeHtml(resource.platform || "Learning Platform")}</h4>
            <div class="resource-tags resource-skill-tags">
                ${skillChips}
            </div>
            <p>${escapeHtml(resource.description || "Learning resources for career development.")}</p>
            <div class="resource-credential ${credential.className}">
                <span class="credential-dot" aria-hidden="true"></span>
                <strong>${credential.label}</strong>
            </div>
            <div class="resource-cost-note">${escapeHtml(resource.learningCost || "Check provider for current terms")}</div>
            ${resource.credentialStatus ? `<details class="credential-details"><summary>Certificate details</summary><p>${escapeHtml(resource.credentialStatus)}</p></details>` : ""}
            ${url ? `<a href="${url}" target="_blank" rel="noopener noreferrer" class="resource-link">Visit official platform <span aria-hidden="true">↗</span></a>` : ""}
        </article>
    `;
}


/* =========================================================
   GENERIC RESULT HELPERS
   ========================================================= */

function renderScoreBar(
    label,
    value,
    maximum
) {

    const numericValue =
        numberValue(
            value
        );


    const max =
        numberValue(
            maximum
        )
        || 100;


    const percent =
        Math.max(
            0,
            Math.min(
                100,
                (
                    numericValue
                    / max
                ) * 100
            )
        );


    return `
        <div>

            <div class="score-row">

                <span class="score-row-label">
                    ${escapeHtml(label)}
                </span>

                <span class="score-row-value">
                    ${numericValue}/${max}
                </span>

            </div>


            <div class="score-bar">

                <div
                    class="score-bar-fill"
                    style="width: ${percent}%">
                </div>

            </div>

        </div>
    `;

}


function renderSkillGroup(
    title,
    items,
    type
) {

    const safeItems =
        safeArray(
            items
        );


    if (!safeItems.length) {
        return "";
    }


    return `
        <div class="result-section">

            <div class="result-section-title">
                ${escapeHtml(title)}
            </div>


            <div class="tag-list">

                ${safeItems
                    .map(
                        item =>
                            `
                                <span class="skill-tag ${type}">
                                    ${escapeHtml(item)}
                                </span>
                            `
                    )
                    .join("")}

            </div>

        </div>
    `;

}


function renderTagSection(
    title,
    items,
    type
) {

    return renderSkillGroup(
        title,
        items,
        type
    );

}


function renderTextListSection(
    title,
    items
) {

    const safeItems =
        safeArray(
            items
        );


    if (!safeItems.length) {
        return "";
    }


    return `
        <div class="result-section">

            <div class="result-section-title">
                ${escapeHtml(title)}
            </div>


            <ul class="list-clean">

                ${safeItems
                    .map(
                        item => `
                            <li>
                                <span>
                                    ${escapeHtml(
                                        typeof item === "string"
                                            ? item
                                            : JSON.stringify(item)
                                    )}
                                </span>
                            </li>
                        `
                    )
                    .join("")}

            </ul>

        </div>
    `;

}


function renderRoadmap(
    title,
    items
) {

    const safeItems =
        safeArray(
            items
        );


    if (!safeItems.length) {
        return "";
    }


    return `
        <div class="result-section">

            <div class="result-section-title">
                ${escapeHtml(title)}
            </div>


            <div class="roadmap">

                ${safeItems
                    .map(
                        (item, index) =>
                            `
                                <div class="roadmap-item">

                                    <div class="roadmap-number">
                                        ${index + 1}
                                    </div>

                                    <div class="roadmap-body">

                                        <strong>
                                            ${escapeHtml(
                                                typeof item === "string"
                                                    ? item
                                                    : (
                                                        item.title
                                                        || item.step
                                                        || item.action
                                                        || JSON.stringify(item)
                                                    )
                                            )}
                                        </strong>


                                        ${
                                            item
                                            && item.description
                                                ? `
                                                    <p>
                                                        ${escapeHtml(
                                                            item.description
                                                        )}
                                                    </p>
                                                `
                                                : ""
                                        }

                                    </div>

                                </div>
                            `
                    )
                    .join("")}

            </div>

        </div>
    `;

}


/* =========================================================
   HEALTH
   ========================================================= */

async function checkHealth() {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/health`
            );


        const data =
            await parseResponse(
                response
            );


        const status =
            document.getElementById(
                "sidebarHealthStatus"
            );


        if (status) {

            status.textContent =
                data.status === "UP"
                    ? "Online"
                    : "Unavailable";

        }


        showToast(
            "System status",
            data.status === "UP"
                ? "AI Career Companion is online."
                : "The application is not fully available."
        );

    } catch (error) {

        const status =
            document.getElementById(
                "sidebarHealthStatus"
            );


        if (status) {

            status.textContent =
                "Offline";

        }

    }

}


/* =========================================================
   DASHBOARD COUNTS
   ========================================================= */

async function loadDashboardStats() {

    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/analyses`
            );


        if (response.ok) {

            const data =
                await response.json();


            document.getElementById(
                "dashboardResumeCount"
            ).textContent =
                Array.isArray(data)
                    ? data.length
                    : 0;

        }

    } catch (error) {

        document.getElementById(
            "dashboardResumeCount"
        ).textContent =
            "—";

    }


    try {

        const response =
            await fetch(
                `${API_BASE_URL}/api/job-matches`
            );


        if (response.ok) {

            const data =
                await response.json();


            document.getElementById(
                "dashboardJobCount"
            ).textContent =
                Array.isArray(data)
                    ? data.length
                    : 0;

        }

    } catch (error) {

        document.getElementById(
            "dashboardJobCount"
        ).textContent =
            "—";

    }

}


/* =========================================================
   HTTP HELPERS
   ========================================================= */

async function parseResponse(
    response
) {

    if (!response.ok) {

        throw new Error(
            await extractError(
                response
            )
        );

    }


    return response.json();

}


async function extractError(
    response
) {

    try {

        const data =
            await response.json();


        if (data.message) {
            return data.message;
        }


        if (data.error) {
            return data.error;
        }


        return "The server returned an error.";

    } catch (error) {

        return (
            `Request failed with status ${response.status}.`
        );

    }

}


/* =========================================================
   VALIDATION
   ========================================================= */

function validatePdf(
    file
) {

    if (!file) {

        showToast(
            "Resume required",
            "Please select a PDF resume."
        );

        return false;
    }


    const isPdf =
        file.type === "application/pdf"
        ||
        file.name
            .toLowerCase()
            .endsWith(".pdf");


    if (!isPdf) {

        showToast(
            "Invalid file",
            "Only PDF resumes are supported."
        );

        return false;
    }


    return true;

}


/* =========================================================
   LOADING
   ========================================================= */

function setLoading(
    button,
    isLoading,
    loadingText
) {

    /*
     * Supports both:
     *
     * setLoading("buttonId", ...)
     *
     * and:
     *
     * setLoading(buttonElement, ...)
     */

    if (typeof button === "string") {

        button =
            document.getElementById(
                button
            );

    }


    if (!button) {
        return;
    }


    if (isLoading) {

        if (!button.dataset.originalText) {

            button.dataset.originalText =
                button.innerHTML;

        }


        button.disabled = true;


        button.innerHTML = `

            <span class="custom-spinner"></span>

            ${escapeHtml(
                loadingText
            )}

        `;

    } else {

        button.disabled = false;


        button.innerHTML =
            button.dataset.originalText
            || escapeHtml(
                loadingText
            );

    }

}


/* =========================================================
   CUSTOM TOAST
   ========================================================= */

function showToast(
    title,
    message
) {

    const container =
        document.getElementById(
            "toastContainer"
        );


    if (!container) {
        return;
    }


    const toast =
        document.createElement(
            "div"
        );


    toast.className =
        "custom-toast";


    toast.innerHTML = `

        <div class="custom-toast-content">

            <div class="custom-toast-title">
                ${escapeHtml(title)}
            </div>

            <div class="custom-toast-message">
                ${escapeHtml(message)}
            </div>

        </div>


        <button
            type="button"
            class="custom-toast-close"
            aria-label="Close">

            ×

        </button>

    `;


    container.appendChild(
        toast
    );


    const closeButton =
        toast.querySelector(
            ".custom-toast-close"
        );


    closeButton.addEventListener(
        "click",
        () => {

            removeToast(
                toast
            );

        }
    );


    setTimeout(
        () => {

            removeToast(
                toast
            );

        },
        4500
    );

}


function removeToast(
    toast
) {

    if (!toast.isConnected) {
        return;
    }


    toast.classList.add(
        "toast-hide"
    );


    setTimeout(
        () => {

            if (toast.isConnected) {

                toast.remove();

            }

        },
        250
    );

}


/* =========================================================
   DISPLAY HELPERS
   ========================================================= */

function showElement(
    id
) {

    const element =
        document.getElementById(
            id
        );


    if (element) {

        element.classList.remove(
            "d-none"
        );

    }

}


function hideElement(
    id
) {

    const element =
        document.getElementById(
            id
        );


    if (element) {

        element.classList.add(
            "d-none"
        );

    }

}


/* =========================================================
   SAFE HELPERS
   ========================================================= */

function safeArray(
    value
) {

    return Array.isArray(value)
        ? value
        : [];

}


function numberValue(
    value
) {

    const number =
        Number(
            value
        );


    return Number.isFinite(number)
        ? number
        : 0;

}


function escapeHtml(
    value
) {

    return String(
        value ?? ""
    )
        .replaceAll(
            "&",
            "&amp;"
        )
        .replaceAll(
            "<",
            "&lt;"
        )
        .replaceAll(
            ">",
            "&gt;"
        )
        .replaceAll(
            '"',
            "&quot;"
        )
        .replaceAll(
            "'",
            "&#039;"
        );

}


function safeUrl(
    value
) {

    const url =
        String(
            value ?? ""
        ).trim();


    if (!/^https?:\/\//i.test(url)) {

        return "#";

    }


    return url.replaceAll(
        '"',
        "%22"
    );

}