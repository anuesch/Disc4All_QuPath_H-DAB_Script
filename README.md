# Disc4All_QuPath

**This script is an easy forward script for the use of immunohitochemically stained slides.**

This script is part of a [H-DAB immunopositivity tutorial](https://disc4all-qupath.gitbook.io/qupath-project/) for low cellular tissue.  

The README File which you are currently on is the file explaining the different steps of the script. There is no need to copy over the codelines, the script to run in QuPath can found on your left hand side downloaded as .groovie file and opened through the script editor in QuPath. There are `//comments` showing you between `//----//`which lines the code needs replacing. They do not affect the script as they are `//ignored`.  


The first part of the script is importing the needed library, getting the project, and setting it to H-DAB
  
```
import static qupath.lib.gui.scripting.QPEx.* 
def project = getProject()

setImageType('BRIGHTFIELD_H_DAB');

```

The next part needs to be adjusted for each batch, it sets the stain values. 

***Replace this line with your own script line generated!***

replace this with your own values EstimatingStainingVectorValues

```
setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');
```
Select the tissue you want to detect, e.g. Region, or NP
```
selectObjectsByClassification("Region")  
```
Replace this with your cell detection values
```
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Hematoxylin OD","requestedPixelSizeMicrons":0.5,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.1,"maxBackground":2.0,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')
```
Replace this with the name of your Objectclassifier
```
runObjectClassifier("ObjectClassifier")
```
**save


def entry = getProjectEntry()
def name = entry.getImageName() + '.txt'
def path = buildFilePath(PROJECT_BASE_DIR, 'Results')
mkdirs(path)
path = buildFilePath(path, name)                             

saveDetectionMeasurements(path)			


println('Results exported to ' + path)
```

