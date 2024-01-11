// QuPath Script for batch processing of H-DAB images

import static qupath.lib.gui.scripting.QPEx.*

def project = getProject()
for (entry in project.getImageList()) {
    def hierarchy = entry.readHierarchy()
    def annotations = hierarchy.getAnnotationObjects()
    print entry.getImageName() + '\t' + annotations.size()
}

setImageType('BRIGHTFIELD_H_DAB');

//Set your own stains
/----/
setColorDeconvolutionStains('{"Name" : "H-DAB estimated", "Stain 1" : "Hematoxylin", "Values 1" : "0.73481 0.63078 0.24935", "Stain 2" : "DAB", "Values 2" : "0.27519 0.51569 0.81138", "Background" : " 255 255 255"}');
/----/

//only includ in automatic tissue detection 
/***********************/

resetSelection();
createAnnotationsFromPixelClassifier("TissueDetection", 1.0E6, 1.0E6, "SPLIT", "DELETE_EXISTING", "SELECT_NEW")
selectObjectsByClassification("Region")
runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons":-100.0,"lineCap":"ROUND","removeInterior":false,"constrainToParent":true}')


clearSelectedObjects()  // Delete the original annotation

/***********************/

selectAnnotations()  // Select all remaining annotations, which will be the target for cell detection


//replace this whit your own cell detection
/----/
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":20.0,"maxAreaMicrons":400.0,"threshold":0.2,"maxBackground":0.25,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

/----/

runObjectClassifier("ObjectClassifier")

// Save measurements to directory called "Results"

def entry = getProjectEntry()
def name = entry.getImageName() + '.txt'
def path = buildFilePath(PROJECT_BASE_DIR, 'Results')
mkdirs(path)
path = buildFilePath(path, name)

saveDetectionMeasurements(path)			
// Can change to "AnnotationMeasurements" if needed

println('Results exported to ' + path)
