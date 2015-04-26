# IRE Major Project

Description

Information Retrieval and Extraction [CSE474]
Document clustering, Feature aggregation & Visualization


Set Up:

1.Clone the Project into your local disk using the command git clone https://github.com/nandkunal/IRE-Major-Project.git

2.Open Eclipse and Right Click on the Project Pane, Select Import  and Choose Maven Project.

3.Browse to samples directory, pom.xml should be visible for selection.

4.Click Ok

5.Maven will start downloading the dependencies(Note: Make sure to add proxy settings if you are connected to internet via proxy)

6.After all dependencies is resolved, right-click on pom.xml,Run As and Click Maven Clean.Build SUCEESS message should be shown in the console

7.Now Right Click pom.xml, Run as Maven Build and Enter below in Goals Field:clean compile

8.Click ok and Maven should compile all the classes.


How To Run:
Part1: Clustering
  1. Download the project
  2. import the project into eclipse as maven project
  3. put all the file set which needs to be clustered into rawdata folder inside the project
  4. Run frontUI.java as Java Application
  5. In the UI select the features based on which analysis to be done
  6. Select the number of clusters and seeds
  7. Press Submit and wait for the popup for Completion message
  8. Go To outputRaw to see the documents clustered.
  

Part2: Visualization
 1.In the UI that comes up for Running the Clustering Part, after clustering is completed the user gets a Confirmation dialogue
 asking "Would you like to Visualize the Cluster?"
 2.On pressing Yes Option to Select either "Bar Graph" or "Word Cloud" on left side of Pane. Select the appropriate one and click "Get Graph". You can see the Visualisation of all the Custers.
   

Project Scope 



Mentor: Nikhil Priyatam
Project No. : 3
Team No. : 7
            
Team Members: Jigar Thakkar, Krishna Gupta, Kunal Khaware, Suhas K S 







    


Abstract
This project is mainly focused on feature identification, clustering and visualization of the set of documents. This will be divided into three phases namely.
Feature Identification and Listing
Document clustering
Visualization of the clusters on bargraph, heatmap and word cloud for analysis.
Given a corpus of documents crawled from the web, which can be html,text, pdf and other types, provide∙ Identify and realize features (such as TFIDF, PoS, NER, length of the document, document sentiment, type of  media, source type etc.) for every document in the corpus.
Based on the requested feature the documents are clustered.  
Provide visual summaries of the clusters. For example, a stacked bar chart for  PoS density, a heat map for co-­occurrence matrix, word (tag) cloud for a cluster.  
Manually label the clusters (either a simple numerical label) and rearrange the  cluster memberships if necessary and classify them. Identify/report the  discriminating features.



Project Scope
The scope of the project is to build a generic platform for clustering of the document where currently document can be clustered based on various features. Hence If all the possible features are identified and listed, the user can select his desired feature and cluster the document. Various tools are used in the project in order to extract feature, cluster and visualisation such as.
A tool to generate features using NLP or other techniques given a  document
A tool to visualize summaries of a group of “related” documents 
A tool to Identify which features are important.








Related Systems
Apache UIMA 
                It  analyze large volumes of unstructured information in order to discover knowledge that is relevant to an end user.

 Lingo3G
            Lingo3G is a software component that organizes collections of text documents into clearly-labeled hierarchical thematic folders called clusters. In real-time, fully automatically and without external knowledge bases
 Carrot2
             Carrot2 is an Open Source Search Results Clustering Engine. It can automatically organize small collections of documents (search results but not only) into thematic categories.
           


4. Proposed System/Approach
Parsing various file formats – To address this we have to try various implementations available on the web JSoup parser, Apache Tika.
 Extract all possible features from text: NE, POS, TF, IDF, etc. – To analyze large volumes of unstructured information in order to discover knowledge that is relevant for our end product, so we are using Apache UIMA in Eclipse.
Need to explore various tools for generating graphs, heat maps, word clouds. This can be achieved using matlab, java birt tool.

