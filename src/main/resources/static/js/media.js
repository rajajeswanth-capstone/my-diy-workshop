function updateLabel(object, labelId) {
  const uploadLabel = document.getElementById(labelId);
  uploadLabel.textContent = object.files[0].name
}

function showMediaType(object) {
  const mediaListingOperationElement = document.getElementById('mediaListingOperation');
  mediaListingOperationElement.value = object.value;
  initMedia();
}

function nextItem() {
  const resourceType = getDisplayedResourceType()
    const prefix = getResourceTypePrefix(resourceType)
    const currentElementName = getResourceTypeCurrentElementName(resourceType)
    const sizeElementName = getResourceTypeSizeElementName(resourceType)

    const currentElement = document.getElementById(currentElementName);
    const currentElementDiv = document.getElementById(prefix + '' + currentElement.value);
    const nextElementDiv = document.getElementById(prefix + '' + (parseInt(currentElement.value) + 1));
    const leftArrowDiv = document.getElementById('leftArrow');
    const stopArrowLeft = document.getElementById('stopArrowLeft');
    const resourceTypeSize = document.getElementById(sizeElementName);

    if (nextElementDiv != undefined) {
      currentElement.value = parseInt(currentElement.value) + 1;
      currentElementDiv.style.display = "none";
      nextElementDiv.style.display = "block";
      setNavigation(resourceTypeSize, currentElement)
    }
}

function previousItem() {
  const resourceType = getDisplayedResourceType()
  const prefix = getResourceTypePrefix(resourceType)
  const currentElementName = getResourceTypeCurrentElementName(resourceType)
  const sizeElementName = getResourceTypeSizeElementName(resourceType)

  const currentElement = document.getElementById(currentElementName);
  const currentElementDiv = document.getElementById(prefix + '' + currentElement.value);
  const nextElementDiv = document.getElementById(prefix + '' + (parseInt(currentElement.value) - 1));
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');
  const resourceTypeSize = document.getElementById(sizeElementName);

  if (nextElementDiv != undefined) {
    currentElement.value = parseInt(currentElement.value) - 1;
    currentElementDiv.style.display = "none";
    nextElementDiv.style.display = "block";
    setNavigation(resourceTypeSize, currentElement)
  }
}

function getDisplayedResourceType() {
  const resourceTypes = ["pictures", "videos", "documents", "weblinks"]
  let result = null;

  for (let i=0;i<resourceTypes.length;i++) {
    let display = document.getElementById(resourceTypes[i]).style.display;
    if (display == "block") {
      result = resourceTypes[i]
    }
  }
  return result;
}

function getResourceTypePrefix(resourceType) {
  if (resourceType == "pictures") {
    return 'picture'
  } else if (resourceType == "videos") {
    return 'video'
  } else if (resourceType == "documents") {
    return 'document'
  } else if (resourceType == "weblinks") {
    return 'weblink'
  }
}

function getResourceTypeCurrentElementName(resourceType) {
  if (resourceType == "pictures") {
    return 'currentPicture'
  } else if (resourceType == "videos") {
    return 'currentVideo'
  } else if (resourceType == "documents") {
    return 'currentDocument'
  } else if (resourceType == "weblinks") {
    return 'currentWeblink'
  }
}

function getResourceTypeSizeElementName(resourceType) {
  if (resourceType == "pictures") {
    return 'picturesSize'
  } else if (resourceType == "videos") {
    return 'videosSize'
  } else if (resourceType == "documents") {
    return 'documentsSize'
  } else if (resourceType == "weblinks") {
    return 'weblinksSize'
  }
}

function setNavigation(projectsSize, currentProject) {
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  if (parseInt(projectsSize.value) == 0) {
    rightArrowDiv.style.display = "none";
    stopArrowRight.style.display = "none";
    leftArrowDiv.style.display = "none";
    stopArrowLeft.style.display = "none";
  } else if (parseInt(projectsSize.value) == 1) {
    rightArrowDiv.style.display = "none";
    stopArrowRight.style.display = "block";
    leftArrowDiv.style.display = "none";
    stopArrowLeft.style.display = "block";
  } else {
    if (parseInt(projectsSize.value) == parseInt(currentProject.value)) {
      rightArrowDiv.style.display = "none";
      stopArrowRight.style.display = "block";
    } else {
      rightArrowDiv.style.display = "block";
      stopArrowRight.style.display = "none";
    }
    if (parseInt(currentProject.value) == 1) {
      leftArrowDiv.style.display = "none";
      stopArrowLeft.style.display = "block";
    } else {
      leftArrowDiv.style.display = "block";
      stopArrowLeft.style.display = "none";
    }
  }
}

function setSequence(resourceType) {
  if (resourceType == "pictures") {
    const picturesSizeElement = document.getElementById('picturesSize');
    const createPictureElement = document.getElementById('createPicture');
    const localResourceSequenceElement = createPictureElement.querySelectorAll('[name="localResourceSequence"]')[0];
    const displaySequenceElement = createPictureElement.querySelectorAll('[name="displaySequence"]')[0];
    const idElement = createPictureElement.querySelectorAll('[name="id"]')[0];
    if (idElement.value == null || idElement.value.length == 0) {
      if (displaySequenceElement != undefined) {
        displaySequenceElement.value = parseInt(picturesSizeElement.value) + 1;
      }
      if (localResourceSequenceElement != undefined) {
        localResourceSequenceElement.value = parseInt(picturesSizeElement.value) + 1;
      }
    }
  } else if (resourceType == "videos") {
    const videosSizeElement = document.getElementById('videosSize');
    const createVideoElement = document.getElementById('createVideo');
    const localResourceSequenceElement = createVideoElement.querySelectorAll('[name="localResourceSequence"]')[0];
    const displaySequenceElement = createVideoElement.querySelectorAll('[name="displaySequence"]')[0];
    const idElement = createVideoElement.querySelectorAll('[name="id"]')[0];
    if (idElement.value == null || idElement.value.length == 0) {
      if (displaySequenceElement != undefined) {
        displaySequenceElement.value = parseInt(videosSizeElement.value) + 1;
      }
      if (localResourceSequenceElement != undefined) {
        localResourceSequenceElement.value = parseInt(videosSizeElement.value) + 1;
      }
    }
  } else if (resourceType == "documents") {
    const documentsSizeElement = document.getElementById('documentsSize');
    const createDocumentElement = document.getElementById('createDocument');
    const localResourceSequenceElement = createDocumentElement.querySelectorAll('[name="localResourceSequence"]')[0];
    const displaySequenceElement = createDocumentElement.querySelectorAll('[name="displaySequence"]')[0];
    const idElement = createDocumentElement.querySelectorAll('[name="id"]')[0];
    if (idElement.value == null || idElement.value.length == 0) {
      if (displaySequenceElement != undefined) {
        displaySequenceElement.value = parseInt(documentsSizeElement.value) + 1;
      }
      if (localResourceSequenceElement != undefined) {
        localResourceSequenceElement.value = parseInt(documentsSizeElement.value) + 1;
      }
    }
  } else if (resourceType == "weblinks") {
    const webLinksSizeElement = document.getElementById('weblinksSize');
    const createWebLinkElement = document.getElementById('createWeblink');
    const localResourceSequenceElement = createWebLinkElement.querySelectorAll('[name="webResourceSequence"]')[0];
    const displaySequenceElement = createWebLinkElement.querySelectorAll('[name="displaySequence"]')[0];
    const idElement = createWebLinkElement.querySelectorAll('[name="id"]')[0];
    if (idElement.value == null || idElement.value.length == 0) {
      if (displaySequenceElement != undefined) {
        displaySequenceElement.value = parseInt(webLinksSizeElement.value) + 1;
      }
      if (localResourceSequenceElement != undefined) {
        localResourceSequenceElement.value = parseInt(webLinksSizeElement.value) + 1;
      }
    }
  }
}

function addResourceTypeElement(resourceType) {
  const resourceTypeCapitalized = resourceType.charAt(0).toUpperCase() + resourceType.slice(1);
  const createElement = document.getElementById('create' + resourceTypeCapitalized);
  const addButtonElement = document.getElementById('add' + resourceTypeCapitalized + 'Button');
  const idElement = createElement.querySelectorAll('[name="id"]')[0];
  const titleElement = createElement.querySelectorAll('[name="title"]')[0];
  const descriptionElement = createElement.querySelectorAll('[name="description"]')[0];
  const sequenceElement = createElement.querySelectorAll('[name="localResourceSequence"]')[0];
  const displaySequenceElement = createElement.querySelectorAll('[name="displaySequence"]')[0];
  const currentElement = document.getElementById('current' + resourceTypeCapitalized);
  const currentDivElement = document.getElementById(resourceType + currentElement.value);
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  idElement.value = null;
  titleElement.value = null;
  descriptionElement.value = null;
  sequenceElement.value = null;
  displaySequenceElement.value = null;
  addButtonElement.value = "Add";
  currentDivElement.style.display = "none";
  createElement.style.display = "block";
  rightArrowDiv.style.display = "none";
  stopArrowRight.style.display = "none";
  leftArrowDiv.style.display = "none";
  stopArrowLeft.style.display = "none";
}

function addWebResource() {
  const createElement = document.getElementById('createWeblink');
  const addButtonElement = document.getElementById('addWeblinkButton');
  const idElement = createElement.querySelectorAll('[name="id"]')[0];
  const titleElement = createElement.querySelectorAll('[name="title"]')[0];
  const descriptionElement = createElement.querySelectorAll('[name="description"]')[0];
  const linkElement = createElement.querySelectorAll('[name="link"]')[0];
  const sequenceElement = createElement.querySelectorAll('[name="webResourceSequence"]')[0];
  const displaySequenceElement = createElement.querySelectorAll('[name="displaySequence"]')[0];
  const currentElement = document.getElementById('currentWeblink');
  const currentDivElement = document.getElementById('weblink' + currentElement.value);
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  idElement.value = null;
  titleElement.value = null;
  descriptionElement.value = null;
  linkElement.value = null;
  sequenceElement.value = null;
  displaySequenceElement.value = null;
  addButtonElement.value = "Add";
  currentDivElement.style.display = "none";
  createElement.style.display = "block";
  rightArrowDiv.style.display = "none";
  stopArrowRight.style.display = "none";
  leftArrowDiv.style.display = "none";
  stopArrowLeft.style.display = "none";
}

function cancelSave(resourceType) {
  const mediaListingOperationElement = document.getElementById('mediaListingOperation');
  mediaListingOperationElement.value = resourceType;
  initMedia();
}

function editResourceTypeElement(resourceType) {
  const resourceTypeCapitalized = resourceType.charAt(0).toUpperCase() + resourceType.slice(1);
  const currentElement = document.getElementById('current' + resourceTypeCapitalized);
  const addButtonElement = document.getElementById('add' + resourceTypeCapitalized + 'Button');
  const currentDivElement = document.getElementById(resourceType + currentElement.value);
  const idFormElement = document.getElementById(resourceType + 'Id' + currentElement.value);
  const localResourceSequenceFormElement = document.getElementById(resourceType + 'LocalResourceSequence' + currentElement.value);
  const displaySequenceFormElement = document.getElementById(resourceType + 'DisplaySequence' + currentElement.value);
  const titleFormElement = document.getElementById(resourceType + 'Title' + currentElement.value);
  const descriptionFormElement = document.getElementById(resourceType + 'Description' + currentElement.value);

  const createElement = document.getElementById('create' + resourceTypeCapitalized);
  const idElement = createElement.querySelectorAll('[name="id"]')[0];
  const titleElement = createElement.querySelectorAll('[name="title"]')[0];
  const descriptionElement = createElement.querySelectorAll('[name="description"]')[0];
  const sequenceElement = createElement.querySelectorAll('[name="localResourceSequence"]')[0];
  const displaySequenceElement = createElement.querySelectorAll('[name="displaySequence"]')[0];

  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  idElement.value = idFormElement.value
  titleElement.value = titleFormElement.value
  descriptionElement.value = descriptionFormElement.value
  sequenceElement.value = localResourceSequenceFormElement.value;
  displaySequenceElement.value = displaySequenceFormElement.value;
  addButtonElement.value = "Update";

  currentDivElement.style.display = "none";
  createElement.style.display = "block";
  rightArrowDiv.style.display = "none";
  stopArrowRight.style.display = "none";
  leftArrowDiv.style.display = "none";
  stopArrowLeft.style.display = "none";
}

function editWebResource() {
  const currentElement = document.getElementById('currentWeblink');
  const addButtonElement = document.getElementById('addWeblinkButton');
  const currentDivElement = document.getElementById('weblink' + currentElement.value);
  const idFormElement = document.getElementById('weblinkId' + currentElement.value);
  const localResourceSequenceFormElement = document.getElementById('weblinkSequence' + currentElement.value);
  const displaySequenceFormElement = document.getElementById('weblinkDisplaySequence' + currentElement.value);
  const titleFormElement = document.getElementById('weblinkTitle' + currentElement.value);
  const descriptionFormElement = document.getElementById('weblinkDescription' + currentElement.value);
  const linkFormElement = document.getElementById('weblinkLink' + currentElement.value);
  const resourceTypeFormElement = document.getElementById('weblinkResourceType' + currentElement.value);

  const createElement = document.getElementById('createWeblink');
  const idElement = createElement.querySelectorAll('[name="id"]')[0];
  const titleElement = createElement.querySelectorAll('[name="title"]')[0];
  const descriptionElement = createElement.querySelectorAll('[name="description"]')[0];
  const sequenceElement = createElement.querySelectorAll('[name="webResourceSequence"]')[0];
  const displaySequenceElement = createElement.querySelectorAll('[name="displaySequence"]')[0];
  const resourceTypeElement = createElement.querySelectorAll('[name="resourceType"]')[0];
  const linkElement = createElement.querySelectorAll('[name="link"]')[0];

  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  idElement.value = idFormElement.value
  titleElement.value = titleFormElement.value
  descriptionElement.value = descriptionFormElement.value
  sequenceElement.value = localResourceSequenceFormElement.value;
  displaySequenceElement.value = displaySequenceFormElement.value;
  resourceTypeElement.value = resourceTypeFormElement.value;
  linkElement.value = linkFormElement.value;
  addButtonElement.value = "Update";

  currentDivElement.style.display = "none";
  createElement.style.display = "block";
  rightArrowDiv.style.display = "none";
  stopArrowRight.style.display = "none";
  leftArrowDiv.style.display = "none";
  stopArrowLeft.style.display = "none";
}

function initMedia() {
  const mediaListingOperationElement = document.getElementById('mediaListingOperation');
  const mediaListingOperation = mediaListingOperationElement.value;
  const leftArrowDiv = document.getElementById('leftArrow');
  const rightArrowDiv = document.getElementById('rightArrow');
  const mediaTypeElement = document.getElementById('mediaType');
  const picturesStates = ["pictures", "pictures_save_success", "pictures_save_errors"]
  const videoStates = ["videos", "videos_save_success", "videos_save_errors"]
  const documentStates = ["documents", "documents_save_success", "documents_save_errors"]
  const webLinkStates = ["weblinks", "weblinks_save_success", "weblinks_save_errors"]
  if (picturesStates.includes(mediaListingOperation)) {
    handlePictures(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement)
  } else if (videoStates.includes(mediaListingOperation)) {
    handleVideos(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement)
  } else if (documentStates.includes(mediaListingOperation)) {
    handleDocuments(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement)
  } else if (webLinkStates.includes(mediaListingOperation)) {
    handleWebLinks(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement)
  }
}

function handleDocuments(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement) {
  const documentsSizeElement = document.getElementById('documentsSize');
  const currentDocumentElement = document.getElementById('currentDocument');
  const createDocumentElement = document.getElementById('createDocument');
  const cancelCreateDocumentElement = document.getElementById('cancelDocumentButton');
  if (mediaListingOperation == "documents") {
    handleCreateOnPageLoad('document', parseInt(documentsSizeElement.value), currentDocumentElement, documentsSizeElement, createDocumentElement, cancelCreateDocumentElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("documents", ["pictures", "videos", "weblinks"])
  } if (mediaListingOperation == "documents_save_success") {
    handleCreateOnPageLoad('document', parseInt(documentsSizeElement.value), currentDocumentElement, documentsSizeElement, createDocumentElement, cancelCreateDocumentElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("documents", ["pictures", "videos", "weblinks"])
  } if (mediaListingOperation == "documents_save_errors") {
    handleCreateOnPageLoad('document', parseInt(documentsSizeElement.value), currentDocumentElement, documentsSizeElement, createDocumentElement, cancelCreateDocumentElement, leftArrowDiv, rightArrowDiv, true)
    toggleMediaType("documents", ["pictures", "videos", "weblinks"])
  }
  mediaTypeElement.value = "documents"
}

function handleWebLinks(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement) {
  const webLinksSizeElement = document.getElementById('weblinksSize');
  const currentWebLinkElement = document.getElementById('currentWeblink');
  const createWebLinkElement = document.getElementById('createWeblink');
  const cancelCreateWebLinkElement = document.getElementById('cancelWeblinkButton');
  if (mediaListingOperation == "weblinks") {
    handleCreateOnPageLoad('weblink', parseInt(webLinksSizeElement.value), currentWebLinkElement, webLinksSizeElement, createWebLinkElement, cancelCreateWebLinkElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("weblinks", ["pictures", "videos", "documents"])
  } if (mediaListingOperation == "weblinks_save_success") {
    handleCreateOnPageLoad('weblink', parseInt(webLinksSizeElement.value), currentWebLinkElement, webLinksSizeElement, createWebLinkElement, cancelCreateWebLinkElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("weblinks", ["pictures", "videos", "documents"])
  } if (mediaListingOperation == "weblinks_save_errors") {
    handleCreateOnPageLoad('weblink', parseInt(webLinksSizeElement.value), currentWebLinkElement, webLinksSizeElement, createWebLinkElement, cancelCreateWebLinkElement, leftArrowDiv, rightArrowDiv, true)
    toggleMediaType("weblinks", ["pictures", "videos", "documents"])
  }
  mediaTypeElement.value = "weblinks"
}

function handleVideos(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement) {
  const videosSizeElement = document.getElementById('videosSize');
  const currentVideoElement = document.getElementById('currentVideo');
  const createVideoElement = document.getElementById('createVideo');
  const cancelCreateVideoElement = document.getElementById('cancelVideoButton');
  if (mediaListingOperation == "videos") {
    handleCreateOnPageLoad('video', parseInt(videosSizeElement.value), currentVideoElement, videosSizeElement, createVideoElement, cancelCreateVideoElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("videos", ["pictures", "documents", "weblinks"])
  } if (mediaListingOperation == "videos_save_success") {
    handleCreateOnPageLoad('video', parseInt(videosSizeElement.value), currentVideoElement, videosSizeElement, createVideoElement, cancelCreateVideoElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("videos", ["pictures", "documents", "weblinks"])
  } if (mediaListingOperation == "videos_save_errors") {
    handleCreateOnPageLoad('video', parseInt(videosSizeElement.value), currentVideoElement, videosSizeElement, createVideoElement, cancelCreateVideoElement, leftArrowDiv, rightArrowDiv, true)
    toggleMediaType("videos", ["pictures", "documents", "weblinks"])
  }
  mediaTypeElement.value = "videos"
}

function handlePictures(mediaListingOperation, leftArrowDiv, rightArrowDiv, mediaTypeElement) {
  const picturesSizeElement = document.getElementById('picturesSize');
  const currentPictureElement = document.getElementById('currentPicture');
  const createPictureElement = document.getElementById('createPicture');
  const cancelCreatePictureElement = document.getElementById('cancelPictureButton');
  const enableDiv = document.getElementById('cancelPictureButton')
  if (mediaListingOperation == "pictures") {
    handleCreateOnPageLoad('picture', parseInt(picturesSizeElement.value), currentPictureElement, picturesSizeElement, createPictureElement, cancelCreatePictureElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("pictures", ["videos", "documents", "weblinks"])
  } if (mediaListingOperation == "pictures_save_success") {
    handleCreateOnPageLoad('picture', parseInt(picturesSizeElement.value), currentPictureElement, picturesSizeElement, createPictureElement, cancelCreatePictureElement, leftArrowDiv, rightArrowDiv, false)
    toggleMediaType("pictures", ["videos", "documents", "weblinks"])
  } if (mediaListingOperation == "pictures_save_errors") {
    handleCreateOnPageLoad('picture', parseInt(picturesSizeElement.value), currentPictureElement, picturesSizeElement, createPictureElement, cancelCreatePictureElement, leftArrowDiv, rightArrowDiv, true)
    toggleMediaType("pictures", ["videos", "documents", "weblinks"])
  }
  mediaTypeElement.value = "pictures"
}

function toggleMediaType(enableDivName, disableDivNames) {
  document.getElementById(enableDivName).style.display = "block";

  for (let i=0;i<disableDivNames.length;i++) {
    document.getElementById(disableDivNames[i]).style.display = "none";
  }
}

function handleCreateOnPageLoad(elementType, size, currentElement, sizeElement, createElement, cancelElement, leftArrowElement, rightArrowElement, createError) {
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');
  const isSharedProjectElement = document.getElementById('isSharedProject');
  const noMediaElement = document.getElementById(elementType + 'NoMedia');
  if (createError || size == 0) {
    createElement.style.display = "block";
    if (size == 0) {
      cancelElement.style.display = "none";
    } else {
      cancelElement.style.display = "block";
    }
    leftArrowDiv.style.display = "none";
    rightArrowDiv.style.display = "none";
    stopArrowRight.style.display = "none";
    stopArrowLeft.style.display = "none";
    if (isSharedProjectElement != undefined && isSharedProjectElement.value == "true") {
      noMediaElement.style.display = "block";
      createElement.style.display = "none";
    }
  } else {
    const currentPictureDivElement = document.getElementById(elementType + currentElement.value);
    createElement.style.display = "none";
    currentPictureDivElement.style.display = "block";
    setNavigation(sizeElement, currentElement)
  }
}

function cancelShare() {
  const shareProjectDiv = document.getElementById('share-project');
  const successDiv = shareProjectDiv.querySelectorAll('[id="success-message"]')[0];

  if (successDiv != undefined) {
    successDiv.textContent = '';
  }
  shareProjectDiv.style.display = "none";
  cancelSave();
}

function hideNavigation() {
  const leftArrowDiv = document.getElementById('leftArrow');
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  leftArrowDiv.style.display = "none";
  rightArrowDiv.style.display = "none";
  stopArrowLeft.style.display = "none";
  stopArrowRight.style.display = "none";
}