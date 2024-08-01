import static qupath.lib.gui.scripting.QPEx.*

def project = getProject()
for (entry in project.getImageList()) {
    def hierarchy = entry.readHierarchy()
    def annotations = hierarchy.getAnnotationObjects()
    print entry.getImageName() + '\t' + annotations.size()
}

setImageType('BRIGHTFIELD_H_DAB');

//NP

setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("NP")  // Select all remaining annotations, which will be the target for cell detection

runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Hematoxylin OD","requestedPixelSizeMicrons":0.5,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.1,"maxBackground":2.0,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_NP")

//CEP

setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("CEP")

runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":5.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.5,"sigmaMicrons":0.8,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.12,"maxBackground":1.5,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_CEP")

//iAF

setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("iAF")

runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.2,"maxBackground":0.35,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_iAF")


//oAF

setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("oAF")

runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.2,"maxBackground":0.35,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_oAF")

//save

def entry = getProjectEntry()
def name = entry.getImageName() + '.txt'
def path = buildFilePath(PROJECT_BASE_DIR, 'Results')
mkdirs(path)
path = buildFilePath(path, name)                             

saveDetectionMeasurements(path)			


println('Results exported to ' + path)