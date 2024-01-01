package com.wizard.xiver.utils

import com.wizard.xiver.CategoryItem

object CategoryUtils {
    @JvmField
    val CATEGORIES = arrayOf(
        CategoryItem(
            "Astrophysics", "astro-ph",
            CategoryItem("All", "astro-ph"),
            CategoryItem("Astrophysics of Galaxies", "astro-ph.GA"),
            CategoryItem("Cosmology and Nongalactic Astrophysics", "astro-ph.CO"),
            CategoryItem("Earth and Planetary Astrophysics", "astro-ph.EP"),
            CategoryItem("High Energy Astrophysical Phenomena", "astro-ph.HE"),
            CategoryItem("Instrumentation and Methods for Astrophysics", "astro-ph.IM"),
            CategoryItem("Solar and Stellar Astrophysics", "astro-ph.SR")
        ),
        CategoryItem(
            "Computer Science", "CoRR",
            CategoryItem("All", "CoRR"),
            CategoryItem("Artificial Intelligence", "cs.AI"),
            CategoryItem("Computation and Language", "cs.CL"),
            CategoryItem("Computational Complexity", "cs.CC"),
            CategoryItem("Computational Engineering, Finance, and Science", "cs.CE"),
            CategoryItem("Computational Geometry", "cs.CG"),
            CategoryItem("Computer Science and Game Theory", "cs.GT"),
            CategoryItem("Computer Vision and Pattern Recognition", "cs.CV"),
            CategoryItem("Computers and Society", "cs.CY"),
            CategoryItem("Cryptography and Security", "cs.CR"),
            CategoryItem("Data Structures and Algorithms", "cs.DS"),
            CategoryItem("Databases", "cs.DB"),
            CategoryItem("Digital Libraries", "cs.DL"),
            CategoryItem("Discrete Mathematics", "cs.DM"),
            CategoryItem("Distributed, Parallel, and Cluster Computing", "cs.DC"),
            CategoryItem("Emerging Technologies", "cs.ET"),
            CategoryItem("Formal Languages and Automata Theory", "cs.FL"),
            CategoryItem("General Literature", "cs.GL"),
            CategoryItem("Graphics", "cs.GR"),
            CategoryItem("Hardware Architecture", "cs.AR"),
            CategoryItem("Human-Computer Interaction", "cs.HC"),
            CategoryItem("Information Retrieval", "cs.IR"),
            CategoryItem("Information Theory", "cs.IT"),
            CategoryItem("Machine Learning", "cs.LG"),
            CategoryItem("Logic in Computer Science", "cs.LO"),
            CategoryItem("Mathematical Software", "cs.MS"),
            CategoryItem("Multiagent Systems", "cs.MA"),
            CategoryItem("Multimedia", "cs.MM"),
            CategoryItem("Networking and Internet Architecture", "cs.NI"),
            CategoryItem("Neural and Evolutionary Computing", "cs.NE"),
            CategoryItem("Numerical Analysis", "cs.NA"),
            CategoryItem("Operating Systems", "cs.OS"),
            CategoryItem("Other", "cs.OH"),
            CategoryItem("Performance", "cs.PF"),
            CategoryItem("Programming Languages", "cs.PL"),
            CategoryItem("Robotics", "cs.RO"),
            CategoryItem("Social and Information Networks", "cs.SI"),
            CategoryItem("Software Engineering", "cs.SE"),
            CategoryItem("Sound", "cs.SD"),
            CategoryItem("Symbolic", "cs.SC"),
            CategoryItem("Systems and Control", "cs.SY")
        ),
        CategoryItem(
            "Condensed Matter", "cond-mat",
            CategoryItem("All", "cond-mat"),
            CategoryItem("Disordered Systems and Neural Networks", "cond-mat.dis-nn"),
            CategoryItem("Materials Science", "cond-mat.mtrl-sci"),
            CategoryItem("Mesoscale and Nanoscale Physics", "cond-mat.mes-hall"),
            CategoryItem("Other Condensed Matter", "cond-mat..other"),
            CategoryItem("Quantum Gases", "cond-mat.quant-gas"),
            CategoryItem("Soft Condensed Matter", "cond-mat.soft"),
            CategoryItem("Statistical Mechanics", "cond-mat.stat-mech"),
            CategoryItem("Strongly Correlated Electrons", "cond-mat.str-el"),
            CategoryItem("Superconductivity", "cond-mat.supr-con")
        ),
        CategoryItem(
            "Economics", "econ",
            CategoryItem("Econometrics", "eess.AS"),
            CategoryItem("General Econpomics", "econ.GN"),
            CategoryItem("Theoretical Economics","econ.TH")
        ),
        CategoryItem(
            "Electrical Engineering and Systems Science", "eess",
            CategoryItem("All", "eess"),
            CategoryItem("Audio and Speech Processing", "eess.AS"),
            CategoryItem("Image and Video Processing", "eess.IV"),
            CategoryItem("Signal Processing", "eess.SP"),
            CategoryItem("Systems and Control", "eess.SY")
        ),
        CategoryItem(
            "General Relativity and Quantum Cosmology", "gr-qc",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "High Energy Physics - Experiment", "hep-ex",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "High Energy Physics - Lattice", "hep-lat",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "High Energy Physics - Phenomenology", "hep-ph",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "High Energy Physics - Theory", "hep-th",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "Mathematics", "math",
            CategoryItem("All", "math"),
            CategoryItem("Algebraic Geometry", "math.AG"),
            CategoryItem("Algebraic Topology", "math.AT"),
            CategoryItem("Analysis of PDEs", "math.AP"),
            CategoryItem("CategoryItem Theory", "math.CT"),
            CategoryItem("Classical Analysis and ODEs", "math.CA"),
            CategoryItem("Combinatorics", "math.CO"),
            CategoryItem("Commutative Algebra", "math.AC"),
            CategoryItem("Complex Variables", "math.CV"),
            CategoryItem("Differential Geometry", "math.DG"),
            CategoryItem("Dynamical Systems", "math.DS"),
            CategoryItem("Functional Analysis", "math.FA"),
            CategoryItem("General Mathematics", "math.GM"),
            CategoryItem("General Topology", "math.GN"),
            CategoryItem("Geometric Topology", "math.GT"),
            CategoryItem("Group Theory", "math.GR"),
            CategoryItem("History and Overview", "math.HO"),
            CategoryItem("Information Theory", "math.IT"),
            CategoryItem("K-Theory and Homology", "math.KT"),
            CategoryItem("Logic", "math.LO"),
            CategoryItem("Mathematical Physics", "math.MP"),
            CategoryItem("Metric Geometry", "math.MG"),
            CategoryItem("Number Theory", "math.NT"),
            CategoryItem("Numerical Analysis", "math.NA"),
            CategoryItem("Operator Algebras", "math.OA"),
            CategoryItem("Optimization and Control", "math.OC"),
            CategoryItem("Probability", "math.PR"),
            CategoryItem("Quantum Algebra", "math.QA"),
            CategoryItem("Representation Theory", "math.RT"),
            CategoryItem("Rings and Algebras", "math.RA"),
            CategoryItem("Spectral Theory", "math.SP"),
            CategoryItem("Statistics Theory", "math.ST"),
            CategoryItem("Symplectic Geometry", "math.SG")
        ),
        CategoryItem(
            "Mathematical Physics", "math-ph",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "Nonlinear Sciences", "nlin",
            CategoryItem("All", "nlin"),
            CategoryItem("Adaptation and Self-Organizing Systems", "nlin.AO"),
            CategoryItem("Cellular Automata and Lattice Gases", "nlin.CG"),
            CategoryItem("Chaotic Dynamics", "nlin.CD"),
            CategoryItem("Exactly Solvable and Integrable Systems", "nlin.SI"),
            CategoryItem("Pattern Formation and Solitons", "nlin.PS")
        ),
        CategoryItem(
            "Nuclear Experiment", "nucl-ex",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "Nuclear Theory", "nucl-th",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "Physics", "physics",
            CategoryItem("All", "physics"),
            CategoryItem("Accelerator Physics", "physics.acc-ph"),
            CategoryItem("Applied Physics", "physics.app-ph"),
            CategoryItem("Atmospheric and Oceanic Physics", "physics.ao-ph"),
            CategoryItem("Atomic Physics", "physics.atom-ph"),
            CategoryItem("Atomic and Molecular Clusters", "physics.atm-clus"),
            CategoryItem("Biological Physics", "physics.bio-ph"),
            CategoryItem("Chemical Physics", "physics.chem-ph"),
            CategoryItem("Classical Physics", "physics.class-ph"),
            CategoryItem("Computational Physics", "physics.comp-ph"),
            CategoryItem("Data Analysis, Statistics and Probability", "physics.data-an"),
            CategoryItem("Fluid Dynamics", "physics.flu-dyn"),
            CategoryItem("General Physics", "physics.gen-ph"),
            CategoryItem("Geophysics", "physics.geo-ph"),
            CategoryItem("History and Philosophy of Physics", "physics.hist-ph"),
            CategoryItem("Instrumentation and Detectors", "physics.ins-det"),
            CategoryItem("Medical Physics", "physics.med-ph"),
            CategoryItem("Optics", "physics.optics"),
            CategoryItem("Physics Education", "physics.ed-ph"),
            CategoryItem("Physics and Society", "physics.soc-ph"),
            CategoryItem("Plasma Physics", "physics.plasm-ph"),
            CategoryItem("Popular Physics", "physics.pop-ph"),
            CategoryItem("Space Physics", "physics.space-ph")
        ),
        CategoryItem(
            "Quantitative Biology", "q-bio",
            CategoryItem("All", "q-bio"),
            CategoryItem("Biomolecules", "q-bio.BM"),
            CategoryItem("Cell Behavior", "q-bio.CB"),
            CategoryItem("Genomics", "q-bio.GN"),
            CategoryItem("Molecular Networks", "q-bio.MN"),
            CategoryItem("Neurons and Cognition", "q-bio.NC"),
            CategoryItem("Other Quantitative Biology", "q-bio.OT"),
            CategoryItem("Populations and Evolution", "q-bio.PE"),
            CategoryItem("Quantitative Methods", "q-bio.QM"),
            CategoryItem("Subcellular Processes", "q-bio.SC"),
            CategoryItem("Tissues and Organs", "q-bio.TO")
        ),
        CategoryItem(
            "Quantitative Finance", "q-fin",
            CategoryItem("All", "q-fin."),
            CategoryItem("Computational Finance", "q-fin.CP"),
            CategoryItem("Economics", "q-fin.EC"),
            CategoryItem("General Finance", "q-fin.GN"),
            CategoryItem("Mathematical Finance", "q-fin.MF"),
            CategoryItem("Portfolio Management", "q-fin.PM"),
            CategoryItem("Pricing of Securities", "q-fin.PR"),
            CategoryItem("Risk Management", "q-fin.RM"),
            CategoryItem("Statistical Finance", "q-fin.ST"),
            CategoryItem("Trading and Market Microstructure", "q-fin.TR")
        ),
        CategoryItem(
            "Quantum Physics", "quant-ph",
            *(null ?: arrayOfNulls<CategoryItem?>(0))
        ),
        CategoryItem(
            "Statistics", "stat",
            CategoryItem("All", "stat"),
            CategoryItem("Applications", "stat.AP"),
            CategoryItem("Computation", "stat.CO"),
            CategoryItem("Machine Learning", "stat.ML"),
            CategoryItem("Methodology", "stat.ME"),
            CategoryItem("Other Statistics", "stat.OT"),
            CategoryItem("Statistics Theory", "stat.TH")
        )
    )
}
