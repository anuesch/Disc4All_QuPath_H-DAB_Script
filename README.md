# Disc4All_QuPath

**This script is an easy forward script for the us of immunohitochemically stained slides.**

The first part of the script is importing the needed library, getting the project and setting it to H-DAB
  
```
import static qupath.lib.gui.scripting.QPEx.* 
def project = getProject()
  for (entry in project.getImageList()) {
      ...
  }

setImageType('BRIGHTFIELD_H_DAB');

```

the next part need to be adjusted for each batch, it sets the stain values. 
```
setColorDeconvolutionStains('..."}');
```
  
fo the automatic tissue detection we first reset the selection
```
resetSelection();
```

we then create with the TissueDetetion thresholder the regions
```
createAnnotationsFromPixelClassifier(...)
```

we then select all the regions
```
selectObjectsByClassification("Region")
```

And remove the edges as those are often stained more intense
```
runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons":-100.0,"lineCap":"ROUND","removeInterior":false,"constrainToParent":true}')
```
then we delete the original annotation
```
clearSelectedObjects()
```

select all remainin annotations which will be tartgeted for cell detection, also if we perform specific region detection manually we use this
```
selectAnnotations()
```

We then run cell detection specifically for the project (change values)

```
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection',       '{...}')
```

Afterwards the detected cells will be classified by the trained object classifier

```
runObjectClassifier("ObjectClassifier")
```

and the measurements will be saved to the directory called results within the project folder
```
def entry = getProjectEntry()
def name = entry.getImageName() + '.txt'
def path = buildFilePath(PROJECT_BASE_DIR, 'Results')
mkdirs(path)
path = buildFilePath(path, name)

saveDetectionMeasurements(path)		
println('Results exported to ' + path)
```

