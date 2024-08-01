import static qupath.lib.gui.scripting.QPEx.*

def project = getProject()

setImageType('BRIGHTFIELD_H_DAB');

//Set your own stains
//----//
setColorDeconvolutionStains('{"Name" : "H-DAB estimated", "Stain 1" : "Hematoxylin", "Values 1" : "0.73481 0.63078 0.24935", "Stain 2" : "DAB", "Values 2" : "0.27519 0.51569 0.81138", "Background" : " 255 255 255"}');
//----//


resetSelection();
createAnnotationsFromPixelClassifier("TissueDetection", 1.0E6, 1.0E6, "SPLIT", "DELETE_EXISTING", "SELECT_NEW")
selectObjectsByClassification("Region")
runPlugin('qupath.lib.plugins.objects.DilateAnnotationPlugin', '{"radiusMicrons":-100.0,"lineCap":"ROUND","removeInterior":false,"constrainToParent":true}')
clearSelectedObjects()  