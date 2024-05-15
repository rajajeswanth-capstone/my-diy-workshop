function initMaterial() {
  const addMaterialErrorsElement = document.getElementById("addMaterialErrors");
  const createMaterialElement = document.getElementById('createMaterial');
  const materialsListElement = document.getElementById('materialsList');
  if (addMaterialErrorsElement.value == "true") {
    materialsListElement.style.display = "none";
    createMaterialElement.style.display = "block";
  }
}

function editBudget() {
  const projectBudgetElement = document.getElementById("projectBudget");
  const projectBudgetFormElement = document.getElementById("budget");
  const hasBudgetElement = document.getElementById("hasBudget");
  const noBudgetElement = document.getElementById("noBudget");
  const projectBudget = projectBudgetElement.textContent;

  projectBudgetFormElement.value = projectBudget;
  hasBudgetElement.setAttribute("hidden", "hidden")
  noBudgetElement.removeAttribute("hidden")
}

function editMaterial(object) {
  const closestRow = object.closest('tr');
  const idValueElement = closestRow.querySelectorAll('[name="idValue"]')[0];
  const vendorValueElement = closestRow.querySelectorAll('[name="vendorValue"]')[0];
  const materialDescriptionValueElement = closestRow.querySelectorAll('[name="materialDescriptionValue"]')[0];
  const unitsValueElement = closestRow.querySelectorAll('[name="unitsValue"]')[0];
  const pricePerUnitValueElement = closestRow.querySelectorAll('[name="pricePerUnitValue"]')[0];
  const materialSequenceValueElement = closestRow.querySelectorAll('[name="materialSequenceValue"]')[0];

  const createMaterialElement = document.getElementById('createMaterial');
  const idFormElement = createMaterialElement.querySelectorAll('[name="id"]')[0];
  const vendorFormElement = createMaterialElement.querySelectorAll('[name="vendor"]')[0];
  const materialDescriptionFormElement = createMaterialElement.querySelectorAll('[name="materialDescription"]')[0];
  const unitsFormElement = createMaterialElement.querySelectorAll('[name="units"]')[0];
  const pricePerUnitFormElement = createMaterialElement.querySelectorAll('[name="pricePerUnit"]')[0];
  const materialSequenceFormElement = createMaterialElement.querySelectorAll('[name="materialSequence"]')[0];

  const materialsListElement = document.getElementById('materialsList');

  idFormElement.value = idValueElement.value
  vendorFormElement.value = vendorValueElement.value
  materialDescriptionFormElement.value = materialDescriptionValueElement.value
  unitsFormElement.value = unitsValueElement.value
  pricePerUnitFormElement.value = pricePerUnitValueElement.value
  materialSequenceFormElement.value = materialSequenceValueElement.value

  materialsListElement.style.display = "none";
  createMaterialElement.style.display = "block";
}

function addMaterial() {
  const createMaterialElement = document.getElementById('createMaterial');
  const materialsListElement = document.getElementById('materialsList');
  const materialSequenceElement = createMaterialElement.querySelectorAll('[name="materialSequence"]')[0];
  const idElement = createMaterialElement.querySelectorAll('[name="id"]')[0];

  materialSequenceElement.value = null;
  idElement.value = null;
  materialsListElement.style.display = "none";
  createMaterialElement.style.display = "block";
}

function cancelSave() {
  const createMaterialElement = document.getElementById('createMaterial');
  const materialsListElement = document.getElementById('materialsList');

  createMaterialElement.style.display = "none";
  materialsListElement.style.display = "block";
}

function setMaterialSequence() {
  const createMaterialElement = document.getElementById('createMaterial');
  const materialSizeElement = document.getElementById('materialSize');
  const materialSequenceElement = createMaterialElement.querySelectorAll('[name="materialSequence"]')[0];
  const idElement = createMaterialElement.querySelectorAll('[name="id"]')[0];

  if (idElement.value == null || idElement.value.length == 0) {
    if (materialSequenceElement != undefined) {
      materialSequenceElement.value = parseInt(materialSizeElement.value) + 1;
    }
  }
}