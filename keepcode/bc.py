
    
    # import libraries
    import numpy as np 
    import matplotlib.pyplot as plt
    import pandas as pd
    #import scipy as sklearn 
    
    
    # import breast cancer dataset
    dataset = pd.read_csv("/Users/emma/Desktop/keepcode/wdbc_data.csv")
    X = dataset.iloc[:, 1:31].values
    Y = dataset.iloc[:, 31].values
    
    
    # to examine dataset
    dataset.head()
    
    # find dimnensions using 'shape'
    print("Cancer data set dimensions : {}".format(dataset.shape))
    
    
    # identifies cancer as M for malignant or B for benign 
    dataset.groupby("diagnosis").size() 
    
    #visualization of data
    dataset.groupby("diagnosis").hist(figsize = (12, 12))
    
     
    # to find missing/null data points
    dataset.isnull().sum()
    dataset.isna().sum()
 
    
    dataframe = pd.DataFrame(Y)
    
    # encodes categorical data values
    from sklearn.preprocessing import LabelEncoder
    labelencoder_Y = LabelEncoder()
    Y = labelencoder_Y.fit_transform(Y)
     
     
    # splitting dataset into training set and test set
    from sklearn.model_selection import train_test_split
    X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size = 0.25, random_state = 0)
     
    # scaling features
    from sklearn.preprocessing import StandardScaler
    sc = StandardScaler()
    X_train = sc.fit_transform(X_train)
    X_test = sc.transform(X_test)
     
     
    # using logistic regression algorithm to training set
    from sklearn.linear_model import LogisticRegression
    classifier = LogisticRegression(random_state = 0)
    classifier.fit(X_train, Y_train)
     
    # using nearest neighbor algorithm
    from sklearn.neighbors import KNeighborsClassifier
    classifier = KNeighborsClassifier(n_neighbors = 5, metric = 'minkowski', p = 2)
    classifier.fit(X_train, Y_train)
     
    # using support vector machines algorithm
    from sklearn.svm import SVC
    classifier = SVC(kernel = 'linear', random_state = 0)
    classifier.fit(X_train, Y_train)
     
    # using kermal svm algortihm
    from sklearn.svm import SVC
    classifier = SVC(kernel = 'rbf', random_state = 0)
    classifier.fit(X_train, Y_train)
     
    # using naive bayes algorithm
    from sklearn.naive_bayes import GaussianNB
    classifier = GaussianNB()
    classifier.fit(X_train, Y_train)
     
    # using decision tree algorithm
    from sklearn.tree import DecisionTreeClassifier
    classifier = DecisionTreeClassifier(criterion = 'entropy', random_state = 0)
    classifier.fit(X_train, Y_train)
     
    # using random forest classsification algorithm
    from sklearn.ensemble import RandomForestClassifier
    classifier = RandomForestClassifier(n_estimators = 10, criterion = 'entropy', random_state = 0)
    classifier.fit(X_train, Y_train)
     
     
    # predict results
    Y_pred = classifier.predict(X_test)
     
    # check accuracy using confusion matrix
    from sklearn.metrics import confusion_matrix
    cm = confusion_matrix(Y_test, Y_pred)
     
     
     
    #random forest classification yields most accurate results in our dataset

     
     
     
     
     
     
     
     
     
     
     
     
     

     
    
    
    