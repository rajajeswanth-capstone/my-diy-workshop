function updateLabel(object) {
  const uploadLabel = document.getElementById('uploadLabel');
  uploadLabel.textContent = object.files[0].name
}

function showProjectType(object) {
  if (object.value == "my") {
    const leftNav = document.getElementById('left-nav');
    const leftNavShared = document.getElementById('left-nav-shared');
    const rightNav = document.getElementById('right-nav');
    const rightNavShared = document.getElementById('right-nav-shared');

    leftNavShared.style.display = "none";
    leftNav.style.display = "block";
    rightNavShared.style.display = "none";
    rightNav.style.display = "block";
    document.getElementById("projects").style.display = "block";
    document.getElementById("sharedProjects").style.display = "none";
  } else if (object.value == "shared") {
    const currentProject = document.getElementById('currentSharedProject');
    const currentProjectDiv = document.getElementById('s' + currentProject.value);
    const leftArrowDiv = document.getElementById('leftArrowShared');
    const rightArrowDiv = document.getElementById('rightArrowShared');
    const projectsSize = document.getElementById('sharedProjectsSize');
    const leftNav = document.getElementById('left-nav');
    const leftNavShared = document.getElementById('left-nav-shared');
    const rightNav = document.getElementById('right-nav');
    const rightNavShared = document.getElementById('right-nav-shared');

    if (currentProjectDiv != undefined) {
      currentProjectDiv.style.display = "block";
    }
    setNavigationShared(sharedProjectsSize, currentProject);
    leftNav.style.display = "none";
    leftNavShared.style.display = "block";
    rightNav.style.display = "none";
    rightNavShared.style.display = "block";
    document.getElementById("sharedProjects").style.display = "block";
    document.getElementById("projects").style.display = "none";
  }
}

function nextSharedProject() {
  const currentProject = document.getElementById('currentSharedProject');
  const currentProjectDiv = document.getElementById('s' + currentProject.value);
  const nextProjectDiv = document.getElementById('s' + (parseInt(currentProject.value) + 1));
  const projectsSize = document.getElementById('sharedProjectsSize');
  const rightArrowDiv = document.getElementById('rightArrowShared');
  const stopArrowRight = document.getElementById('stopArrowRightShared');

  if (nextProjectDiv != undefined) {
    currentProject.value = parseInt(currentProject.value) + 1;
    currentProjectDiv.style.display = "none";
    nextProjectDiv.style.display = "block";
    setNavigationShared(projectsSize, currentProject)
  }
}

function previousSharedProject() {
  const currentProject = document.getElementById('currentSharedProject');
  const currentProjectDiv = document.getElementById('s' + currentProject.value);
  const nextProjectDiv = document.getElementById('s' + (parseInt(currentProject.value) - 1));
  const leftArrowDiv = document.getElementById('leftArrowShared');
  const stopArrowLeft = document.getElementById('stopArrowLeftShared');
  const projectsSize = document.getElementById('sharedProjectsSize');

  if (nextProjectDiv != undefined) {
    currentProject.value = parseInt(currentProject.value) - 1;
    currentProjectDiv.style.display = "none";
    nextProjectDiv.style.display = "block";
    setNavigationShared(projectsSize, currentProject)
  }
}

function nextProject() {
  const currentProject = document.getElementById('currentProject');
  const currentProjectDiv = document.getElementById(currentProject.value);
  const nextProjectDiv = document.getElementById(parseInt(currentProject.value) + 1);
  const projectsSize = document.getElementById('projectsSize');
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');

  if (nextProjectDiv != undefined) {
    currentProject.value = parseInt(currentProject.value) + 1;
    currentProjectDiv.style.display = "none";
    nextProjectDiv.style.display = "block";
    setNavigation(projectsSize, currentProject)
  }
}

function previousProject() {
  const currentProject = document.getElementById('currentProject');
  const currentProjectDiv = document.getElementById(currentProject.value);
  const nextProjectDiv = document.getElementById(parseInt(currentProject.value) - 1);
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');
  const projectsSize = document.getElementById('projectsSize');

  if (nextProjectDiv != undefined) {
    currentProject.value = parseInt(currentProject.value) - 1;
    currentProjectDiv.style.display = "none";
    nextProjectDiv.style.display = "block";
    setNavigation(projectsSize, currentProject)
  }
}

function setNavigationShared(projectsSize, currentProject) {
  const rightArrowDiv = document.getElementById('rightArrowShared');
  const stopArrowRight = document.getElementById('stopArrowRightShared');
  const leftArrowDiv = document.getElementById('leftArrowShared');
  const stopArrowLeft = document.getElementById('stopArrowLeftShared');

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

function setNavigation(projectsSize, currentProject) {
  const rightArrowDiv = document.getElementById('rightArrow');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const leftArrowDiv = document.getElementById('leftArrow');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

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

function initProjects() {
  const projectsSize = document.getElementById('projectsSize');
  const sharedProjectsSize = document.getElementById('sharedProjectsSize');
  const currentProject = document.getElementById('currentProject');
  const currentSharedProject = document.getElementById('currentSharedProject');
  const leftArrowDiv = document.getElementById('leftArrow');
  const rightArrowDiv = document.getElementById('rightArrow');
  const size = parseInt(projectsSize.value);
  const createProjectDiv = document.getElementById('create');
  const addButtonDiv = document.getElementById('addButton');
  const cancelButtonDiv = document.getElementById('cancelButton');
  const projectListingOperationHiddenElement = document.getElementById('projectListingOperation');

  if (size == 0) {
    createProjectDiv.style.display = "block";
    cancelButtonDiv.style.display = "none";
    leftArrowDiv.style.display = "none";
    rightArrowDiv.style.display = "none";
  } else if (projectListingOperationHiddenElement.value == "save") {
    createProjectDiv.style.display = "block";
    if (size == 0) {
      cancelButtonDiv.style.display = "none";
    } else {
      cancelButtonDiv.style.display = "block";
    }
    leftArrowDiv.style.display = "none";
    rightArrowDiv.style.display = "none";
  } else if (projectListingOperationHiddenElement.value == "update") {
    createProjectDiv.style.display = "block";
    cancelButtonDiv.style.display = "block";
    leftArrowDiv.style.display = "none";
    rightArrowDiv.style.display = "none";
    addButtonDiv.value = "Update";
  } else {
    const currentProjectDiv = document.getElementById(currentProject.value);
    currentProjectDiv.style.display = "block";
    createProjectDiv.style.display = "none";
    cancelButtonDiv.style.display = "block";
    leftArrowDiv.style.display = "block";
    rightArrowDiv.style.display = "block";
    setNavigation(projectsSize, currentProject);
    setNavigationShared(sharedProjectsSize, currentSharedProject);
    if (projectListingOperationHiddenElement.value == "share") {
      shareProject();
    }
  }
}

function cancelSave() {
  const projectsSize = document.getElementById('projectsSize');
  const currentProject = document.getElementById('currentProject');
  const leftArrowDiv = document.getElementById('leftArrow');
  const rightArrowDiv = document.getElementById('rightArrow');
  const size = parseInt(projectsSize.value);
  const createProjectDiv = document.getElementById('create');
  const cancelButtonDiv = document.getElementById('cancelButton');
  const projectListingOperationHiddenElement = document.getElementById('projectListingOperation');

  if (size == 0) {
    createProjectDiv.style.display = "block";
    cancelButtonDiv.style.display = "none";
    leftArrowDiv.style.display = "none";
    rightArrowDiv.style.display = "none";
  } else {
    const currentProjectDiv = document.getElementById(currentProject.value);
    currentProjectDiv.style.display = "block";
    createProjectDiv.style.display = "none";
    cancelButtonDiv.style.display = "block";
    setNavigation(projectsSize, currentProject);
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

function editProject() {
  const currentProject = document.getElementById('currentProject');
  const currentProjectDiv = document.getElementById(currentProject.value);
  const currentProjectInt = parseInt(currentProject.value);
  const currentTitle = document.getElementById('title' + currentProjectInt);
  const currentId = document.getElementById('id' + currentProjectInt);
  const currentShortDescription = document.getElementById('shortDescription' + currentProjectInt);
  const createProjectDiv = document.getElementById('create');
  const cancelButtonDiv = document.getElementById('cancelButton');
  const addButtonDiv = document.getElementById('addButton');
  const leftArrowDiv = document.getElementById('leftArrow');
  const rightArrowDiv = document.getElementById('rightArrow');
  const uploadLabelDiv = document.getElementById('uploadLabel');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  currentProjectDiv.style.display = "none";
  document.getElementsByName('id')[0].value = currentId.value;
  document.getElementsByName('title')[0].value = currentTitle.value;
  document.getElementsByName('title')[0].classList.add("form-control-textbox");
  document.getElementsByName('title')[0].classList.remove("form-control-textbox-error");
  document.getElementsByName('shortDescription')[0].value = currentShortDescription.value;
  document.getElementsByName('displaySequence')[0].value = currentProject.value;
  uploadLabelDiv.textContent = "Upload Image or Keep Existing";
  addButtonDiv.value = "Update";
  createProjectDiv.style.display = "block";
  cancelButtonDiv.style.display = "block";
  leftArrowDiv.style.display = "none";
  rightArrowDiv.style.display = "none";
  stopArrowRight.style.display = "none";
  stopArrowLeft.style.display = "none";
}

function addProject() {
  const projectsSize = document.getElementById('projectsSize');
  const currentProject = document.getElementById('currentProject');
  const currentProjectDiv = document.getElementById(currentProject.value);
  const currentProjectInt = parseInt(currentProject.value);
  const createProjectDiv = document.getElementById('create');
  const cancelButtonDiv = document.getElementById('cancelButton');
  const addButtonDiv = document.getElementById('addButton');
  const leftArrowDiv = document.getElementById('leftArrow');
  const rightArrowDiv = document.getElementById('rightArrow');
  const uploadLabelDiv = document.getElementById('uploadLabel');
  const stopArrowRight = document.getElementById('stopArrowRight');
  const stopArrowLeft = document.getElementById('stopArrowLeft');

  currentProjectDiv.style.display = "none";
  document.getElementsByName('id')[0].value = "";
  document.getElementsByName('title')[0].value = "";
  document.getElementsByName('title')[0].classList.add("form-control-textbox");
  document.getElementsByName('title')[0].classList.remove("form-control-textbox-error");
  document.getElementsByName('shortDescription')[0].value = "";
  document.getElementsByName('displaySequence')[0].value = parseInt(projectsSize.value) + 1;
  uploadLabelDiv.textContent = "Upload Image";
  addButtonDiv.value = "Add";
  createProjectDiv.style.display = "block";
  cancelButtonDiv.style.display = "block";
  leftArrowDiv.style.display = "none";
  rightArrowDiv.style.display = "none";
  stopArrowLeft.style.display = "none";
  stopArrowRight.style.display = "none";
}

function shareProject() {
  const currentProject = document.getElementById('currentProject');
  const shareProjectDiv = document.getElementById('share-project');
  const currentProjectDiv = document.getElementById(currentProject.value);
  const currentProjectId = document.getElementById('id' + currentProject.value);
  const projectIdDiv = shareProjectDiv.querySelectorAll('[name="projectId"]')[0]

  if (projectIdDiv != undefined) {
    projectIdDiv.value = currentProjectId.value
  }
  currentProjectDiv.style.display = "none";
  shareProjectDiv.style.display = "block";
  hideNavigation();
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