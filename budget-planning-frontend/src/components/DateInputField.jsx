import InputField from "./InputField";

const DateInputField = ({ label, name, value, setFormData }) => {
    return <InputField label={label} name={name} value={value} setFormData={setFormData} type="date" />;
};

export default DateInputField;