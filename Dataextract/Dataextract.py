import pandas as pd

# Load your Excel or CSV file
df = pd.read_excel("C:/Users/HP/Downloads/archive/Specialist.xlsx")  # or pd.read_excel("file.xlsx")

# Assume the last column is the specialist/target label
# And all other columns are symptoms (0 or 1)
symptom_columns = df.columns[:-1]
target_column = df.columns[-1]

# Convert rows into human-readable text documents for RAG
with open("converted_medical_dataset.md", "w") as f:
    for index, row in df.iterrows():
        # Find all columns where the value is 1 (active symptoms)
        active_symptoms = [col for col in symptom_columns if row[col] == 1]
        specialty = row[target_column]

        # Format into a natural language sentence
        symptoms_str = ", ".join(active_symptoms)

        f.write(f"### Case Profile\n")
        f.write(f"- **Patient Symptoms:** {symptoms_str}\n")
        f.write(f"- **Recommended Medical Specialty:** {specialty}\n\n")

print("Successfully converted matrix to RAG-ready markdown!")