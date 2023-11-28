export function showValidationErrors(res) {
    const nameError = document.getElementById('nameError');
    const descriptionError = document.getElementById('descriptionError');

    nameError.textContent = res.name
        ? res.name
        : '';

    descriptionError.textContent = res.description
        ? res.description
        : '';
}