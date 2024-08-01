import static qupath.lib.gui.scripting.QPEx.*

def project = getProject()

setImageType('BRIGHTFIELD_H_DAB');

//Components can be added and removed project accordingly, in this example we have 4 components (NP,iAF,oAF and CEP)

//---------------------------------------------------------------------------------------------------------------//
//Component1 = The name of your calssification for the tissue e.g. NP

// replace values with values from EstimatingStainingVectors_Component1 (=EstimationStainingVectors_NP)
setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("Component1")  // Change this to the Classification name e.g NP

// replace values with CellDetection_component1 values
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Hematoxylin OD","requestedPixelSizeMicrons":0.5,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.1,"maxBackground":2.0,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_Component_1") // repalce with Objectclassifier with Classification name eg. Objectclassifier_NP
//---------------------------------------------------------------------------------------------------------------//

//can be deleted if only one region analysed
//---------------------------------------------------------------------------------------------------------------//
//Component2 = The name of your calssification for the tissue e.g. iAF

// replace values with values from EstimatingStainingVectors_Component2 (=EstimationStainingVectors_iAF)
setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("Component2") // Change this to the Classification name e.g iAF

// replace values with CellDetection_component2 values
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":5.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.5,"sigmaMicrons":0.8,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.12,"maxBackground":1.5,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_Component2") // repalce with Objectclassifier with Classification name eg. Objectclassifier_iAF
//---------------------------------------------------------------------------------------------------------------//


//can be deleted if only two regions analysed
//---------------------------------------------------------------------------------------------------------------//
//Component3 = The name of your calssification for the tissue e.g. oAF

// replace values with values from EstimatingStainingVectors_Component3 (=EstimationStainingVectors_oAF)
setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("Component3") // Change this to the Classification name e.g oAF

// replace values with CellDetection_component3 values
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.2,"maxBackground":0.35,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_Component3") // Change this to the Classification name e.g oAF
//---------------------------------------------------------------------------------------------------------------//


//can be deleted if only three regions analysed or copeied if more then 4 are beeing analysed
//---------------------------------------------------------------------------------------------------------------//
//Component4 = The name of your calssification for the tissue e.g. NP

// replace values with values from EstimatingStainingVectors_Component3 (=EstimationStainingVectors_NP)
setColorDeconvolutionStains('{"Name" : "H-DAB default", "Stain 1" : "Hematoxylin", "Values 1" : "0.65111 0.70119 0.29049", "Stain 2" : "DAB", "Values 2" : "0.26917 0.56824 0.77759", "Background" : " 255 255 255"}');

selectObjectsByClassification("Component4") // Change this to the Classification name e.g NP

// replace values with CellDetection_component4 values
runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImageBrightfield":"Optical density sum","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":8.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":400.0,"threshold":0.2,"maxBackground":0.35,"watershedPostProcess":true,"excludeDAB":false,"cellExpansionMicrons":5.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')

runObjectClassifier("ObjectClassifier_Component4") // Change this to the Classification name e.g CEP
//---------------------------------------------------------------------------------------------------------------//


//save

def entry = getProjectEntry()
def name = entry.getImageName() + '.txt'
def path = buildFilePath(PROJECT_BASE_DIR, 'Results')
mkdirs(path)
path = buildFilePath(path, name)                             

saveDetectionMeasurements(path)			


println('Results exported to ' + path)