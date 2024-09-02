function initInstruction() {
  const addInstructionErrorsElement = document.getElementById("addInstructionErrors");
  const createInstructionElement = document.getElementById('createInstruction');
  const instructionListElement = document.getElementById('instructionList');
  if (addInstructionErrorsElement.value == "true") {
    instructionListElement.style.display = "none";
    createInstructionElement.style.display = "block";
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

function editInstruction(object) {
  const closestRow = object.closest('table');
  const idValueElement = closestRow.querySelectorAll('[name="idValue"]')[0];
  const titleValueElement = closestRow.querySelectorAll('[name="titleValue"]')[0];
  const instructionValueElement = closestRow.querySelectorAll('[name="instructionValue"]')[0];
  const instructionSequenceValueElement = closestRow.querySelectorAll('[name="instructionSequenceValue"]')[0];

  const createInstructionElement = document.getElementById('createInstruction');
  const addInstructionButton = document.getElementById('addInstructionButton');
  const addInstructionTitle = document.getElementById('addInstructionTitle');
  const idFormElement = createInstructionElement.querySelectorAll('[name="id"]')[0];
  const titleFormElement = createInstructionElement.querySelectorAll('[name="title"]')[0];
  const instructionFormElement = createInstructionElement.querySelectorAll('[name="instruction"]')[0];
  const instructionSequenceFormElement = createInstructionElement.querySelectorAll('[name="instructionSequence"]')[0];

  const instructionListElement = document.getElementById('instructionList');

  idFormElement.value = idValueElement.value
  titleFormElement.value = titleValueElement.value
  instructionFormElement.value = instructionValueElement.value
  instructionSequenceFormElement.value = instructionSequenceValueElement.value

  addInstructionButton.value = "Update";
  addInstructionTitle.textContent = "UPDATE";

  instructionListElement.style.display = "none";
  createInstructionElement.style.display = "block";
}

function addInstruction() {
  const createInstructionElement = document.getElementById('createInstruction');
  const instructionListElement = document.getElementById('instructionList');
  const instructionSequenceElement = createInstructionElement.querySelectorAll('[name="instructionSequence"]')[0];
  const idElement = createInstructionElement.querySelectorAll('[name="id"]')[0];

  instructionSequenceElement.value = null;
  idElement.value = null;
  instructionListElement.style.display = "none";
  createInstructionElement.style.display = "block";
}

function cancelSave() {
  const createInstructionElement = document.getElementById('createInstruction');
  const instructionListElement = document.getElementById('instructionList');

  createInstructionElement.style.display = "none";
  instructionListElement.style.display = "block";
}

function setInstructionSequence() {
  const createInstructionElement = document.getElementById('createInstruction');
  const instructionsSizeElement = document.getElementById('instructionsSize');
  const instructionSequenceElement = createInstructionElement.querySelectorAll('[name="instructionSequence"]')[0];
  const idElement = createInstructionElement.querySelectorAll('[name="id"]')[0];

  if (idElement.value == null || idElement.value.length == 0) {
    if (instructionSequenceElement != undefined) {
      instructionSequenceElement.value = parseInt(instructionsSizeElement.value) + 1;
    }
  }
}