	function handleIntegerInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    const currentValue = inputElement.value;

    if (isNaN(currentValue) || /[^0-9]/.test(currentValue)) {
        errorMessage.textContent = 'Only numbers are allowed.';
        inputElement.value = currentValue.replace(/\D/g, '');
    } else {
        if (currentValue.length > maxLength) {
            errorMessage.textContent = `Only ${maxLength} digits are allowed.`;
            inputElement.value = currentValue.slice(0, maxLength);
        } else {
            errorMessage.textContent = '';
        }
    }
    inputElement.value = inputElement.value.replace(/^0+(?=\d)/, '').slice(0, maxLength);
}

function validateIntegerInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    const currentValue = inputElement.value;

    if (!isNaN(currentValue) && currentValue.length <= maxLength) {
        errorMessage.textContent = '';
    }
}
// --------------------------------------------------------------------------------handling
// letter
function handleLetterInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    let currentValue = inputElement.value;
    // Allow letters, spaces, and dots, but not at the beginning
    const allowedPattern = /^[a-zA-Z][a-zA-Z\s.]*$/;
    // Check if the first character is a letter
    if (currentValue.length > 0 && !/^[a-zA-Z]/.test(currentValue[0])) {
        errorMessage.textContent = 'only letter are allowed';
        currentValue = currentValue.replace(/^[^a-zA-Z]+/, '');
    } else if (!allowedPattern.test(currentValue)) {
        errorMessage.textContent = 'Only letters are allowed.';
        currentValue = currentValue.replace(/[^a-zA-Z\s.]/g, '');
    } else {
        if (currentValue.length > maxLength) {
            errorMessage.textContent = `Only ${maxLength} characters are allowed.`;
            currentValue = currentValue.slice(0, maxLength);
        } else {
            errorMessage.textContent = '';
        }
    }

    inputElement.value = currentValue.slice(0, maxLength);
}
 
function validateLetterInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    const currentValue = inputElement.value;

    if (/^[a-zA-Z]+$/.test(currentValue) && currentValue.length <= maxLength) {
        errorMessage.textContent = '';
    }
}

// --------------------------------------------------------------------------------------valid
// double

function handleDoubleInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    let currentValue = inputElement.value;

    console.log("Initial Input:", currentValue);

    // Clear existing error messages
    errorMessage.textContent = '';

    // Remove all invalid characters except digits and one decimal point
    currentValue = currentValue.replace(/[^0-9.]/g, '');
    console.log("After removing invalid characters:", currentValue);

    // Count the number of decimal points
    const decimalCount = (currentValue.match(/\./g) || []).length;
    console.log("Number of decimal points:", decimalCount);

    if (decimalCount > 1) {
        errorMessage.textContent = 'Only one decimal point is allowed.';
        const parts = currentValue.split('.');
        currentValue = parts[0] + '.' + parts.slice(1).join('').replace(/\./g, '');
        inputElement.value = currentValue;
        console.log("After fixing multiple decimal points:", currentValue);
        return;
    }

    if (currentValue.startsWith('0') && !currentValue.startsWith('0.')) {
        currentValue = currentValue.replace(/^0+/, '');
    }
    console.log("After removing leading zeros:", currentValue);

    const parts = currentValue.split('.');
    let integerPart = parts[0] || '';
    let decimalPart = parts[1] || '';

    console.log("Integer Part:", integerPart);
    console.log("Decimal Part:", decimalPart);

    if (integerPart.length > maxLength) {
        errorMessage.textContent = `Input exceeds the maximum length of ${maxLength} digits.`;
        integerPart = integerPart.slice(0, maxLength);
        decimalPart = '';
        console.log("Integer part after trimming to maxLength:", integerPart);
    } else {
        const availableLengthForDecimal = maxLength - integerPart.length;
        if (decimalPart.length > availableLengthForDecimal) {
            errorMessage.textContent = `Input exceeds the maximum length of ${maxLength} digits.`;
        }
        decimalPart = decimalPart.slice(0, availableLengthForDecimal);
        console.log("Decimal part after trimming to available length:", decimalPart);
    }

    if (decimalPart) {
        inputElement.value = `${integerPart}.${decimalPart}`;
    } else {
        inputElement.value = integerPart;
        if (currentValue.endsWith('.')) {
            inputElement.value += '.';
        }
    }
    console.log("Final Input Value:", inputElement.value);
}


function handleDoubleInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    let currentValue = inputElement.value;

    // Clear the error message initially
    errorMessage.textContent = '';

    // Allow only numbers and a single decimal point
    const validValue = currentValue.replace(/[^0-9.]/g, '');
    if (validValue !== currentValue) {
        errorMessage.textContent = 'Only numbers and a single decimal point are allowed.';
        currentValue = validValue;
    }

    // Count decimal points
    const decimalCount = (currentValue.match(/\./g) || []).length;
    if (decimalCount > 1) {
        errorMessage.textContent = 'Only one decimal point is allowed.';
        const parts = currentValue.split('.');
        currentValue = parts[0] + '.' + parts.slice(1).join('');
    }

    // Handle leading zeros (allow "0." but not unnecessary leading zeros)
    if (currentValue.startsWith('0') && !currentValue.startsWith('0.') && currentValue.length > 1) {
        currentValue = currentValue.replace(/^0+/, '') || '0';
    }

    // Split into integer and decimal parts
    const parts = currentValue.split('.');
    let integerPart = parts[0];
    let decimalPart = parts[1] || '';

    // Allow typing a decimal point after a number (e.g., "123.")
    if (currentValue.endsWith('.') && decimalCount === 1) {
        inputElement.value = currentValue;
        return;
    }

    // Limit decimal places to 2
    if (decimalPart.length > 2) {
        errorMessage.textContent = 'Only 2 decimal places are allowed.';
        decimalPart = decimalPart.slice(0, 2);
    }

    // Enforce maximum length constraint (integer + decimal combined)
    if (integerPart.length + decimalPart.length > maxLength) {
        errorMessage.textContent = `Input exceeds the maximum length of ${maxLength} digits.`;
        const allowedDecimalLength = Math.max(0, maxLength - integerPart.length);
        decimalPart = decimalPart.slice(0, allowedDecimalLength);
        integerPart = integerPart.slice(0, maxLength - decimalPart.length);
    }

    // Update the input element's value
    if (decimalPart) {
        inputElement.value = `${integerPart}.${decimalPart}`;
    } else {
        inputElement.value = integerPart;
    }
   
    
    
    // -------------------------------------------------------------------------------------alphanumric
    

  function handleAlphaNumInput(inputElement, errorElementId, maxLength,
  minLength) {
  const errorMessage = document.getElementById(errorElementId);
  let currentValue = inputElement.value;
 
  // Remove non-alphanumeric characters (only letters and numbers allowed)
  let sanitizedValue = currentValue.replace(/[^a-zA-Z0-9]/g, '');
 
  // Ensure the first character is a letter or number
  if (sanitizedValue.length > 0 && !/^[a-zA-Z0-9]/.test(sanitizedValue[0])) {
  errorMessage.textContent = 'Only alphanumeric characters are allowed.';
  sanitizedValue = sanitizedValue.replace(/^[^a-zA-Z0-9]+/, '');
  }
 
  // Enforce max length
  if (sanitizedValue.length > maxLength) {
  sanitizedValue = sanitizedValue.substring(0, maxLength);
  errorMessage.textContent = `Only ${maxLength} characters are allowed.`;
  }
 
  // Enforce min length (only when input is not empty)
  else if (sanitizedValue.length < minLength && sanitizedValue.length > 0) {
  errorMessage.textContent = `Minimum ${minLength} characters are required.`;
  }
 
  // Clear error if valid
  else {
  errorMessage.textContent = '';
  }
 
  // Update the input field with the sanitized value
  inputElement.value = sanitizedValue;
  }
  
  
  
  function validateAlphaNumericInput(inputElement, errorElementId) {
	    const errorMessage = document.getElementById(errorElementId);
	    const currentValue = inputElement.value.trim(); // Trim to remove leading
														// and trailing whitespace

	    // Regular expression to match exactly 5 alphabetic characters followed by
		// exactly 3 numeric characters
	    const regex = /^[a-zA-Z]{2}[0-9]{3}$/;

	    if (!regex.test(currentValue)) {
	        errorMessage.textContent = 'Please enter exactly 2 alphabetic characters followed by 3 numeric characters.';
	        inputElement.value = '';
	    } else {
	        errorMessage.textContent = '';
	    }
	}
 
    
//    function limitInputLength(textarea, maxLength) {
//        textarea.addEventListener("input", function () {
//            if (this.value.length > maxLength) {
//                this.value = this.value.substring(0, maxLength);
//                alert(`Maximum ${maxLength} characters allowed.`);
//            }
//        });
//    }
    
 //-----------------------------------------------------------------------------------------mobileNo   
    function handleMobileNumberInput(inputElement, errorElementId, maxLength) {
        const errorMessage = document.getElementById(errorElementId);
        const currentValue = inputElement.value.trim();

        if (isNaN(currentValue) || /[^0-9]/.test(currentValue)) {
            errorMessage.textContent = 'Please enter only numbers.';
            inputElement.value = currentValue.replace(/\D/g, '');
        } else {
            if (currentValue.length > maxLength) {
                errorMessage.textContent = `Maximum ${maxLength} digits allowed.`;
                inputElement.value = currentValue.slice(0, maxLength);
            } else {
                if (currentValue.length > 0 && currentValue.charAt(0) <= '5') {
                    errorMessage.textContent = 'Mobile number must start with a digit greater than 5.';
                    inputElement.value = '';
                } else {
                    errorMessage.textContent = '';
                }
            }
        }
    } 
  
    
    






// ---------------------------------------------------------------------------------------mobile
// no

function handleMobileNumberInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    const currentValue = inputElement.value.trim();

    if (isNaN(currentValue) || /[^0-9]/.test(currentValue)) {
        errorMessage.textContent = 'Please enter only numbers.';
        inputElement.value = currentValue.replace(/\D/g, '');
    } else {
        if (currentValue.length > maxLength) {
            errorMessage.textContent = `Maximum ${maxLength} digits allowed.`;
            inputElement.value = currentValue.slice(0, maxLength);
        } else {
            if (currentValue.length > 0 && currentValue.charAt(0) <= '5') {
                errorMessage.textContent = 'Mobile number must start with a digit greater than 5.';
                inputElement.value = '';
            } else {
                errorMessage.textContent = '';
            }
        }
    }
}



function initializeDatePicker(datePickerId, errorElementId, dateFormat = 'mm/dd/yy') {
    if (!$(`#${datePickerId}`).length) {
        return;
    }

    $(function() {
        $(`#${datePickerId}`).datepicker({
            dateFormat: dateFormat,
            onSelect: function() {
                clearErrorMessage(errorElementId);
            }
        });
    });
}

function setupDropdownListener(dropdownName, errorElementId) {
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            if (mutation.type === 'childList') {
                const dropdowns = document.getElementsByTagName("select");

                for (let i = 0; i < dropdowns.length; i++) {
                    if (dropdowns[i].name === dropdownName && !dropdowns[i].dataset.listenerAdded) {
                        dropdowns[i].dataset.listenerAdded = 'true';
                        dropdowns[i].addEventListener("change", function() {
                            clearErrorMessage(errorElementId);
                        });
                    }
                }
            }
        });
    });

    observer.observe(document.body, { childList: true, subtree: true });
}








function clearErrorMessage(errorElementId) {
    const errorElement = document.getElementById(errorElementId);
    if (errorElement) {
        errorElement.innerText = '';
    }
}








/*
 * function handleDoubleInput(inputElement, errorElementId, maxLength) { const
 * errorMessage = document.getElementById(errorElementId); let currentValue =
 * inputElement.value;
 * 
 * errorMessage.textContent = '';
 * 
 * if (isNaN(currentValue) || /[^\d.]/.test(currentValue)) {
 * errorMessage.textContent = 'Only numbers and a decimal point are allowed.';
 * inputElement.value = currentValue.replace(/[^\d.]/g, ''); return; }
 * 
 * currentValue = currentValue.replace(/^0+(?=\d)/, '');
 * 
 * const parts = currentValue.split('.'); let integerPart = parts[0]; let
 * decimalPart = parts[1] || '';
 * 
 * if (currentValue.endsWith('.')) { if (decimalPart.length === 0) { decimalPart =
 * ''; } }
 * 
 * if (integerPart.length + decimalPart.length > maxLength) {
 * errorMessage.textContent = `Input exceeds the maximum length of ${maxLength}
 * digits.`; if (decimalPart) { const availableLengthForDecimal = maxLength -
 * integerPart.length; decimalPart = decimalPart.slice(0,
 * availableLengthForDecimal); inputElement.value =
 * `${integerPart}.${decimalPart}`; } else { inputElement.value =
 * integerPart.slice(0, maxLength); } return; } else { errorMessage.textContent =
 * ''; }
 * 
 * if (decimalPart) { const availableLengthForDecimal = maxLength -
 * integerPart.length; decimalPart = decimalPart.slice(0,
 * availableLengthForDecimal); inputElement.value =
 * `${integerPart}.${decimalPart}`; } else { inputElement.value =
 * integerPart.slice(0, maxLength); }
 * 
 * if (currentValue.includes('.') && decimalPart === '') { inputElement.value =
 * integerPart + '.'; } }
 */

function validateDoubleInput(inputElement, errorElementId, maxLength) {
    const errorMessage = document.getElementById(errorElementId);
    const currentValue = inputElement.value;

    errorMessage.textContent = '';

    if (isNaN(currentValue) || /[^0-9.]/.test(currentValue)) {
        errorMessage.textContent = 'Only numbers and a single decimal point are allowed.';
        return;
    }

    if ((currentValue.match(/\./g) || []).length > 1) {
        errorMessage.textContent = 'Only one decimal point is allowed.';
        return;
    }

    const parts = currentValue.split('.');
    const integerPart = parts[0];
    const decimalPart = parts[1] || '';

    if (integerPart.length + decimalPart.length > maxLength) {
        errorMessage.textContent = `Input exceeds the maximum length of ${maxLength} digits.`;
        return;
    }

    errorMessage.textContent = '';
}





    
    
    function numberLength(input) {
		if (input.value.length > 10) {
			input.value = input.value.slice(0, 10);
		}
	}
}


