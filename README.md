# Disc4All_QuPath

**This script is an easy forward script for the use of immunohitochemically stained slides.**

This script is part of a [H-DAB immunopositivity tutorial](https://app.gitbook.com/o/kAkxf5RLoV6dm2APAW3P/s/SleK316zl0BYwa7DfK2J/~/changes/9/qupath-h-dab-docs/qupath-script) for low cellular tissue.  

The README File which you are currently on is the file explaining the different steps of the script. 
The script can be downloaded as QuPath_HDAB_Batchprocessing.groovy file and the changes needed can be directly made in there. It will tell you within the script too which lines need replacing. 
I suggest you download the groovy file, open it in the script editor in QuPath and follow the README file for the changes and the understanding. 

The first part of the script is importing the needed library, getting the project and setting it to H-DAB
  
```
import static qupath.lib.gui.scripting.QPEx.* 
def project = getProject()
  for (entry in project.getImageList()) {
      ...
  }

setImageType('BRIGHTFIELD_H_DAB');

```

The next part need to be adjusted for each batch, it sets the stain values. 

***Replace this line with your own code line generated!***
```
setColorDeconvolutionStains('{"Name" : "H-DAB estimated", "Stain 1" : "Hematoxylin", "Values 1" : "0.73481 0.63078 0.24935", "Stain 2" : "DAB", "Values 2" : "0.27519 0.51569 0.81138", "Background" : " 255 255 255"}');
```
If you have performed manual tissue detection, the next few code lines can be ignored.   
For the automatic tissue detection we first reset the selection.
```
resetSelection();
```

Create the "Regions" with the TissueDetection threshold. 
```
createAnnotationsFromPixelClassifier("TissueDetection", 1.0E6, 1.0E6, "SPLIT", "DELETE_EXISTING", "SELECT_NEW")
```

Select all the regions.
```
selectObjectsByClassification("Region")
```

And remove the edges as those are often stained more intense.
```
runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons":-100.0,"lineCap":"ROUND","removeInterior":false,"constrainToParent":true}')
```
The original annotation gets then deleted to only exclude the edges of the tissue. 
```
clearSelectedObjects()
```

The remaining annotations will be tartgeted for cell detection.

If specific region detection was performed manually this line is used too. 
```
selectAnnotations()
```

Cell detection is then run specific for the project. 
***Values should be adjusted project specific***

```
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":20.0,"maxAreaMicrons":400.0,"threshold":0.2,"maxBackground":0.25,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')
```

Afterwards the detected cells will be classified by the trained object classifier.

```
runObjectClassifier("ObjectClassifier")
```

The measurements will be saved to the directory called Results within the project folder
```
def entry = getProjectEntry()
def name = entry.getImageName() + '.txt'
def path = buildFilePath(PROJECT_BASE_DIR, 'Results')
mkdirs(path)
path = buildFilePath(path, name)

saveDetectionMeasurements(path)		
println('Results exported to ' + path)
```

